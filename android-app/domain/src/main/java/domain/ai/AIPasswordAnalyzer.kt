package domain.ai

import domain.Password

interface AIPasswordAnalyzer {
    suspend fun analyzePassword(password: String): AIPasswordAnalysis
    suspend fun generateSecurePassword(length: Int = 16): Password
    suspend fun detectPatterns(passwords: List<String>): List<SecurityPattern>
}