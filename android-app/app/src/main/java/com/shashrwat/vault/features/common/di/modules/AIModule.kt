package com.shashrwat.vault.features.common.di.modules

import domain.EnhancedPasswordChecker
import domain.PasswordChecker
import domain.ZxcvbnPasswordChecker
import domain.ai.AIPasswordAnalyzer
import domain.ai.AIPasswordAnalyzerImpl

/**
 * Dependency injection module for AI-powered password analysis
 */
object AIModule {
    
    fun provideAIPasswordAnalyzer(): AIPasswordAnalyzer {
        return AIPasswordAnalyzerImpl()
    }
    
    fun provideEnhancedPasswordChecker(
        traditionalChecker: PasswordChecker,
        aiAnalyzer: AIPasswordAnalyzer
    ): EnhancedPasswordChecker {
        return EnhancedPasswordChecker(traditionalChecker, aiAnalyzer)
    }
}