package domain

import domain.ai.AIPasswordAnalyzer
import domain.ai.AIPasswordAnalysis

/**
 * Enhanced password checker that combines traditional zxcvbn with AI analysis
 */
class EnhancedPasswordChecker(
    private val traditionalChecker: PasswordChecker,
    private val aiAnalyzer: AIPasswordAnalyzer
) : PasswordChecker {
    
    override fun checkStrength(password: Password): PasswordStrength? {
        return traditionalChecker.checkStrength(password)
    }
    
    override fun checkStatus(password: Password): PasswordStatus {
        return traditionalChecker.checkStatus(password)
    }
    
    /**
     * Get comprehensive AI analysis
     */
    suspend fun getAIAnalysis(password: Password): AIPasswordAnalysis {
        return aiAnalyzer.analyzePassword(password)
    }
    
    /**
     * Generate AI-powered secure password
     */
    suspend fun generateSmartPassword(length: Int = 16): Password {
        return aiAnalyzer.generateSmartPassword(length = length)
    }
}