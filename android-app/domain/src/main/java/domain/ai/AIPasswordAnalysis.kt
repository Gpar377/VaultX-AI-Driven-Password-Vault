package domain.ai

import domain.PasswordStrength

data class AIPasswordAnalysis(
    val securityScore: Float,
    val aiStrength: PasswordStrength,
    val vulnerabilities: List<Vulnerability>,
    val patterns: List<DetectedPattern>,
    val entropy: Double,
    val predictability: Float,
    val recommendations: List<SecurityRecommendation>
)

data class Vulnerability(
    val type: VulnerabilityType,
    val severity: VulnerabilitySeverity,
    val description: String,
    val impact: String
)

data class DetectedPattern(
    val type: PatternType,
    val confidence: Float,
    val description: String
)

data class SecurityRecommendation(
    val type: RecommendationType,
    val priority: RecommendationPriority,
    val title: String,
    val description: String,
    val actionable: Boolean = true
)

data class ThreatPattern(
    val type: ThreatType,
    val confidence: Float,
    val description: String,
    val riskLevel: RiskLevel
)

enum class VulnerabilityType {
    DICTIONARY_WORD, COMMON_PATTERN, KEYBOARD_WALK, 
    REPEATED_CHARS, SEQUENTIAL_CHARS, PERSONAL_INFO,
    LEAKED_PASSWORD, WEAK_ENTROPY
}

enum class VulnerabilitySeverity { LOW, MEDIUM, HIGH, CRITICAL }
enum class PatternType { ALPHABETIC, NUMERIC, ALPHANUMERIC, SPECIAL_CHARS, MIXED_CASE, KEYBOARD_PATTERN, DATE_PATTERN, NAME_PATTERN }
enum class RecommendationType { LENGTH_INCREASE, COMPLEXITY_BOOST, PATTERN_BREAK, ENTROPY_IMPROVE, UNIQUENESS_ENHANCE }
enum class RecommendationPriority { LOW, MEDIUM, HIGH, URGENT }
enum class ThreatType { BRUTE_FORCE_VULNERABLE, DICTIONARY_ATTACK, SOCIAL_ENGINEERING, CREDENTIAL_STUFFING, RAINBOW_TABLE, HYBRID_ATTACK }
enum class RiskLevel { MINIMAL, LOW, MODERATE, HIGH, SEVERE }