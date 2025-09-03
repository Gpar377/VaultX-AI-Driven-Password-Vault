package domain.ai

data class AIPasswordAnalysis(
    val securityScore: Int,
    val vulnerabilities: List<SecurityVulnerability>,
    val patterns: List<SecurityPattern>,
    val recommendations: List<SecurityRecommendation>,
    val entropy: Double,
    val estimatedCrackTime: String
)

data class SecurityVulnerability(
    val type: VulnerabilityType,
    val severity: VulnerabilitySeverity,
    val description: String,
    val affectedPortion: String? = null
)

data class SecurityPattern(
    val type: PatternType,
    val confidence: Double,
    val description: String
)

data class SecurityRecommendation(
    val priority: RecommendationPriority,
    val action: String,
    val reason: String
)

enum class VulnerabilityType {
    DICTIONARY_WORD,
    COMMON_PASSWORD,
    KEYBOARD_PATTERN,
    REPEATED_CHARACTERS,
    SEQUENTIAL_CHARACTERS,
    PERSONAL_INFO,
    WEAK_ENTROPY,
    PREDICTABLE_PATTERN
}

enum class VulnerabilitySeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}

enum class PatternType {
    KEYBOARD_WALK,
    SUBSTITUTION,
    REPETITION,
    SEQUENCE,
    DATE_PATTERN,
    COMMON_FORMAT
}

enum class RecommendationPriority {
    LOW, MEDIUM, HIGH, CRITICAL
}