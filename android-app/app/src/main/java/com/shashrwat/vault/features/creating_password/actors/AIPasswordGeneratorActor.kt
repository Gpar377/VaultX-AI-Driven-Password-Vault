package com.shashrwat.vault.features.creating_password.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.creating_password.CreatingPasswordMvi.Effect
import com.shashrwat.vault.features.creating_password.CreatingPasswordMvi.Event
import domain.EnhancedPasswordChecker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Actor for AI-powered password generation
 */
class AIPasswordGeneratorActor(
    private val enhancedPasswordChecker: EnhancedPasswordChecker
) : Actor<Effect, Event> {
    
    override fun handle(effect: Effect): Flow<Event> = flow {
        when (effect) {
            is Effect.GenerateAIPassword -> {
                try {
                    val aiPassword = enhancedPasswordChecker.generateSmartPassword(effect.length)
                    emit(Event.OnPasswordGenerated(aiPassword))
                } catch (e: Exception) {
                    emit(Event.OnPasswordGenerationFailed(e.message ?: "AI generation failed"))
                }
            }
            else -> {
                // Handle other effects normally
            }
        }
    }
}