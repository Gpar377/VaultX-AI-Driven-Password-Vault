package domain.ai

import domain.Password
import domain.PasswordStrength
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.log2
import kotlin.math.min
import kotlin.math.pow

class AIPasswordAnalyzerImpl : AIPasswordAnalyzer {
    
    private val commonPasswords = setOf("password", "123456", "password123", "admin", "qwerty", "letmein")
    private val keyboardPatterns = listOf("qwerty", "asdf", "zxcv", "123456", "098765")
    
    override suspend fun analyzePassword(password: Password): AIPasswordAnalysis = withContext(Dispatchers.Default) {
        val pwd = password.stringData
        val vulnerabilities = detectVulnerabilities(pwd)
        val patterns = detectPatterns(pwd)
        val entropy = calculateEntropy(pwd)
        val predictability = calculatePredictability(pwd)
        val securityScore = calculateSecurityScore(pwd, vulnerabilities, entropy, predictability)
        val aiStrength = determineAIStrength(securityScore)
        val recommendations = generateRecommendations(pwd, vulnerabilities, patterns)
        
        AIPasswordAnalysis(securityScore, aiStrength, vulnerabilities, patterns, entropy, predictability, recommendations)
    }
    
    override suspend fun getSecurityRecommendations(password: Password): List<SecurityRecommendation> = 
        analyzePassword(password).recommendations
    
    override suspend fun detectThreatPatterns(password: Password): List<ThreatPattern> = withContext(Dispatchers.Default) {
        val pwd = password.stringData
        val threats = mutableListOf<ThreatPattern>()
        
        if (pwd.length < 8) {
            threats.add(ThreatPattern(ThreatType.BRUTE_FORCE_VULNERABLE, 0.9f, "Short password vulnerable to brute force", RiskLevel.HIGH))
        }
        
        if (commonPasswords.any { pwd.lowercase().contains(it) }) {
            threats.add(ThreatPattern(ThreatType.DICTIONARY_ATTACK, 0.85f, "Contains common dictionary words", RiskLevel.HIGH))
        }
        
        threats
    }
    
    override suspend fun generateSmartPassword(basePassword: Password?, length: Int): Password = withContext(Dispatchers.Default) {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-="
        val random = kotlin.random.Random.Default
        
        val generated = buildString {
            repeat(length) { append(chars[random.nextInt(chars.length)]) }
        }
        
        Password(optimizeGeneratedPassword(generated))
    }
    
    private fun detectVulnerabilities(password: String): List<Vulnerability> {
        val vulnerabilities = mutableListOf<Vulnerability>()
        
        if (commonPasswords.any { password.lowercase().contains(it) }) {
            vulnerabilities.add(Vulnerability(VulnerabilityType.DICTIONARY_WORD, VulnerabilitySeverity.HIGH, 
                "Contains common dictionary words", "Vulnerable to dictionary attacks"))
        }
        
        keyboardPatterns.forEach { pattern ->
            if (password.lowercase().contains(pattern)) {
                vulnerabilities.add(Vulnerability(VulnerabilityType.KEYBOARD_WALK, VulnerabilitySeverity.MEDIUM,
                    "Contains keyboard pattern: $pattern", "Predictable pattern reduces security"))
            }
        }
        
        if (calculateEntropy(password) < 30.0) {
            vulnerabilities.add(Vulnerability(VulnerabilityType.WEAK_ENTROPY, VulnerabilitySeverity.HIGH,
                "Low entropy indicates predictable password", "Easily crackable by automated tools"))
        }
        
        return vulnerabilities
    }
    
    private fun detectPatterns(password: String): List<DetectedPattern> {
        val patterns = mutableListOf<DetectedPattern>()
        
        if (password.any { it.isUpperCase() } && password.any { it.isLowerCase() }) {
            patterns.add(DetectedPattern(PatternType.MIXED_CASE, 1.0f, "Uses mixed case letters"))
        }
        
        if (password.any { it.isDigit() }) {
            patterns.add(DetectedPattern(PatternType.NUMERIC, 1.0f, "Contains numeric characters"))
        }
        
        if (password.any { !it.isLetterOrDigit() }) {
            patterns.add(DetectedPattern(PatternType.SPECIAL_CHARS, 1.0f, "Contains special characters"))
        }
        
        return patterns
    }
    
    private fun calculateEntropy(password: String): Double {
        if (password.isEmpty()) return 0.0
        
        val charsetSize = when {
            password.any { !it.isLetterOrDigit() } -> 94
            password.any { it.isDigit() } && password.any { it.isLetter() } -> 62
            password.any { it.isDigit() } -> 10
            password.any { it.isUpperCase() } && password.any { it.isLowerCase() } -> 52
            else -> 26
        }
        
        return password.length * log2(charsetSize.toDouble())
    }
    
    private fun calculatePredictability(password: String): Float {
        var score = 0f
        if (commonPasswords.any { password.lowercase().contains(it) }) score += 0.4f
        if (keyboardPatterns.any { password.lowercase().contains(it) }) score += 0.3f
        if (password.matches(Regex(".*\\d{4}.*"))) score += 0.2f
        return min(score, 1.0f)
    }
    
    private fun calculateSecurityScore(password: String, vulnerabilities: List<Vulnerability>, entropy: Double, predictability: Float): Float {
        var score = 1.0f
        
        vulnerabilities.forEach { vuln ->
            score -= when (vuln.severity) {
                VulnerabilitySeverity.CRITICAL -> 0.4f
                VulnerabilitySeverity.HIGH -> 0.25f
                VulnerabilitySeverity.MEDIUM -> 0.15f
                VulnerabilitySeverity.LOW -> 0.05f
            }
        }
        
        val entropyScore = min(entropy / 60.0, 1.0).toFloat()
        score = (score + entropyScore) / 2
        score -= predictability * 0.3f
        
        return maxOf(score, 0.0f)
    }
    
    private fun determineAIStrength(securityScore: Float): PasswordStrength {
        return when {
            securityScore >= 0.8f -> PasswordStrength.SECURE
            securityScore >= 0.6f -> PasswordStrength.STRONG
            securityScore >= 0.4f -> PasswordStrength.MEDIUM
            else -> PasswordStrength.WEAK
        }
    }
    
    private fun generateRecommendations(password: String, vulnerabilities: List<Vulnerability>, patterns: List<DetectedPattern>): List<SecurityRecommendation> {
        val recommendations = mutableListOf<SecurityRecommendation>()
        
        if (password.length < 12) {
            recommendations.add(SecurityRecommendation(RecommendationType.LENGTH_INCREASE, RecommendationPriority.HIGH,
                "Increase Password Length", "Use at least 12 characters for better security"))
        }
        
        if (patterns.none { it.type == PatternType.SPECIAL_CHARS }) {
            recommendations.add(SecurityRecommendation(RecommendationType.COMPLEXITY_BOOST, RecommendationPriority.MEDIUM,
                "Add Special Characters", "Include symbols like !@#$% to increase complexity"))
        }
        
        if (vulnerabilities.any { it.type == VulnerabilityType.DICTIONARY_WORD }) {
            recommendations.add(SecurityRecommendation(RecommendationType.PATTERN_BREAK, RecommendationPriority.HIGH,
                "Avoid Dictionary Words", "Replace common words with unique combinations"))
        }
        
        return recommendations
    }
    
    private fun optimizeGeneratedPassword(password: String): String = password
}