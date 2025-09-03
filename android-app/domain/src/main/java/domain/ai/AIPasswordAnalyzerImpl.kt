package domain.ai

import domain.Password
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.log2
import kotlin.math.pow

class AIPasswordAnalyzerImpl : AIPasswordAnalyzer {

    private val commonPasswords = setOf(
        "password", "123456", "password123", "admin", "qwerty", "letmein",
        "welcome", "monkey", "dragon", "master", "shadow", "football"
    )

    private val keyboardPatterns = listOf(
        "qwerty", "asdf", "zxcv", "123456", "098765"
    )

    override suspend fun analyzePassword(password: String): AIPasswordAnalysis = withContext(Dispatchers.Default) {
        val vulnerabilities = detectVulnerabilities(password)
        val patterns = detectPatterns(listOf(password))
        val entropy = calculateEntropy(password)
        val securityScore = calculateSecurityScore(password, vulnerabilities, entropy)
        val recommendations = generateRecommendations(vulnerabilities, securityScore)
        val crackTime = estimateCrackTime(entropy)

        AIPasswordAnalysis(
            securityScore = securityScore,
            vulnerabilities = vulnerabilities,
            patterns = patterns,
            recommendations = recommendations,
            entropy = entropy,
            estimatedCrackTime = crackTime
        )
    }

    override suspend fun generateSecurePassword(length: Int): Password = withContext(Dispatchers.Default) {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-="
        val generatedString = buildString {
            repeat(length) { append(chars.random()) }
        }
        
        // FIXED: Use Password.create() to handle encryption internally
        Password.create(optimizeGeneratedPassword(generatedString))
    }

    override suspend fun detectPatterns(passwords: List<String>): List<SecurityPattern> = withContext(Dispatchers.Default) {
        passwords.flatMap { password ->
            mutableListOf<SecurityPattern>().apply {
                keyboardPatterns.forEach { pattern ->
                    if (password.lowercase().contains(pattern)) {
                        add(SecurityPattern(
                            type = PatternType.KEYBOARD_WALK,
                            confidence = 0.9,
                            description = "Contains keyboard pattern: $pattern"
                        ))
                    }
                }

                if (hasRepeatedChars(password)) {
                    add(SecurityPattern(
                        type = PatternType.REPETITION,
                        confidence = 0.8,
                        description = "Contains repeated character sequences"
                    ))
                }

                if (hasSequentialChars(password)) {
                    add(SecurityPattern(
                        type = PatternType.SEQUENCE,
                        confidence = 0.85,
                        description = "Contains sequential characters"
                    ))
                }
            }
        }
    }

    private fun detectVulnerabilities(password: String): List<SecurityVulnerability> {
        val vulnerabilities = mutableListOf<SecurityVulnerability>()

        if (commonPasswords.contains(password.lowercase())) {
            vulnerabilities.add(SecurityVulnerability(
                type = VulnerabilityType.COMMON_PASSWORD,
                severity = VulnerabilitySeverity.CRITICAL,
                description = "This is a commonly used password"
            ))
        }

        if (password.length < 8) {
            vulnerabilities.add(SecurityVulnerability(
                type = VulnerabilityType.WEAK_ENTROPY,
                severity = VulnerabilitySeverity.HIGH,
                description = "Password is too short (less than 8 characters)"
            ))
        }

        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }

        val varietyCount = listOf(hasLower, hasUpper, hasDigit, hasSymbol).count { it }
        if (varietyCount < 3) {
            vulnerabilities.add(SecurityVulnerability(
                type = VulnerabilityType.WEAK_ENTROPY,
                severity = VulnerabilitySeverity.MEDIUM,
                description = "Password lacks character variety"
            ))
        }

        if (hasRepeatedChars(password)) {
            vulnerabilities.add(SecurityVulnerability(
                type = VulnerabilityType.REPEATED_CHARACTERS,
                severity = VulnerabilitySeverity.MEDIUM,
                description = "Contains repeated character sequences"
            ))
        }

        return vulnerabilities
    }

    private fun calculateEntropy(password: String): Double {
        val charsetSize = when {
            password.any { it.isLowerCase() } && password.any { it.isUpperCase() } &&
            password.any { it.isDigit() } && password.any { !it.isLetterOrDigit() } -> 94
            password.any { it.isLetter() } && password.any { it.isDigit() } &&
            password.any { !it.isLetterOrDigit() } -> 84
            password.any { it.isLetter() } && password.any { it.isDigit() } -> 62
            password.any { it.isLetter() } -> 52
            else -> 10
        }
        return password.length * log2(charsetSize.toDouble())
    }

    private fun calculateSecurityScore(password: String, vulnerabilities: List<SecurityVulnerability>, entropy: Double): Int {
        var score = 100

        vulnerabilities.forEach { vuln ->
            score -= when (vuln.severity) {
                VulnerabilitySeverity.CRITICAL -> 40
                VulnerabilitySeverity.HIGH -> 25
                VulnerabilitySeverity.MEDIUM -> 15
                VulnerabilitySeverity.LOW -> 5
            }
        }

        score += when {
            entropy >= 60 -> 10
            entropy >= 40 -> 5
            entropy < 25 -> -20
            else -> 0
        }

        return maxOf(0, minOf(100, score))
    }

    private fun generateRecommendations(vulnerabilities: List<SecurityVulnerability>, score: Int): List<SecurityRecommendation> {
        val recommendations = mutableListOf<SecurityRecommendation>()

        if (score < 50) {
            recommendations.add(SecurityRecommendation(
                priority = RecommendationPriority.CRITICAL,
                action = "Replace this password immediately",
                reason = "Password security score is critically low"
            ))
        }

        vulnerabilities.forEach { vuln ->
            when (vuln.type) {
                VulnerabilityType.COMMON_PASSWORD -> recommendations.add(SecurityRecommendation(
                    priority = RecommendationPriority.CRITICAL,
                    action = "Use a unique, uncommon password",
                    reason = "Common passwords are easily guessed"
                ))
                VulnerabilityType.WEAK_ENTROPY -> recommendations.add(SecurityRecommendation(
                    priority = RecommendationPriority.HIGH,
                    action = "Increase password length and character variety",
                    reason = "More entropy makes passwords harder to crack"
                ))
                else -> {}
            }
        }

        return recommendations
    }

    private fun estimateCrackTime(entropy: Double): String {
        val seconds = 2.0.pow(entropy - 1) / 1_000_000_000
        return when {
            seconds < 60 -> "Less than a minute"
            seconds < 3600 -> "${(seconds / 60).toInt()} minutes"
            seconds < 86400 -> "${(seconds / 3600).toInt()} hours"
            seconds < 31536000 -> "${(seconds / 86400).toInt()} days"
            else -> "${(seconds / 31536000).toInt()} years"
        }
    }

    private fun hasRepeatedChars(password: String): Boolean {
        return password.windowed(3).any { window ->
            window[0] == window[1] && window[1] == window[2]
        }
    }

    private fun hasSequentialChars(password: String): Boolean {
        return password.windowed(3).any { window ->
            val chars = window.map { it.code }
            chars[1] == chars[0] + 1 && chars[2] == chars[1] + 1
        }
    }

    private fun optimizeGeneratedPassword(password: String): String {
        // Simple optimization to avoid common patterns
        return password.toCharArray().apply {
            shuffle()
        }.concatToString()
    }
}