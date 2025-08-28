package domain.ai

import domain.Password

interface AIPasswordAnalyzer {
    suspend fun analyzePassword(password: Password): AIPasswordAnalysis
    suspend fun getSecurityRecommendations(password: Password): List<SecurityRecommendation>
    suspend fun detectThreatPatterns(password: Password): List<ThreatPattern>
    suspend fun generateSmartPassword(basePassword: Password? = null, length: Int = 16): Password
}