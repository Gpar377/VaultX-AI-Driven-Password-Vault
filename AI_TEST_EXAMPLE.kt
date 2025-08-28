// AI Password Analyzer Test Example
// This demonstrates how to use the new AI features

import domain.Password
import domain.EnhancedPasswordChecker
import domain.ZxcvbnPasswordChecker
import domain.ai.AIPasswordAnalyzerImpl
import com.nulabinc.zxcvbn.Zxcvbn

suspend fun testAIPasswordAnalyzer() {
    // Setup
    val zxcvbn = Zxcvbn()
    val traditionalChecker = ZxcvbnPasswordChecker(zxcvbn)
    val aiAnalyzer = AIPasswordAnalyzerImpl()
    val enhancedChecker = EnhancedPasswordChecker(traditionalChecker, aiAnalyzer)
    
    // Test weak password
    val weakPassword = Password("password123")
    val analysis = enhancedChecker.getAIAnalysis(weakPassword)
    
    println("=== AI Analysis Results ===")
    println("Password: ${weakPassword.stringData}")
    println("Security Score: ${(analysis.securityScore * 100).toInt()}%")
    println("AI Strength: ${analysis.aiStrength}")
    println("Entropy: ${analysis.entropy.toInt()} bits")
    println("Predictability: ${(analysis.predictability * 100).toInt()}%")
    
    println("\n=== Vulnerabilities ===")
    analysis.vulnerabilities.forEach { vuln ->
        println("• ${vuln.type}: ${vuln.description} (${vuln.severity})")
    }
    
    println("\n=== Recommendations ===")
    analysis.recommendations.forEach { rec ->
        println("• ${rec.title}: ${rec.description} (${rec.priority})")
    }
    
    // Test AI password generation
    println("\n=== AI Generated Password ===")
    val aiPassword = enhancedChecker.generateSmartPassword(16)
    println("Generated: ${aiPassword.stringData}")
    
    val aiAnalysis = enhancedChecker.getAIAnalysis(aiPassword)
    println("AI Password Score: ${(aiAnalysis.securityScore * 100).toInt()}%")
    println("AI Password Strength: ${aiAnalysis.aiStrength}")
}

/*
Expected Output:
=== AI Analysis Results ===
Password: password123
Security Score: 25%
AI Strength: WEAK
Entropy: 42 bits
Predictability: 40%

=== Vulnerabilities ===
• DICTIONARY_WORD: Contains common dictionary words (HIGH)
• WEAK_ENTROPY: Low entropy indicates predictable password (HIGH)

=== Recommendations ===
• Increase Password Length: Use at least 12 characters for better security (HIGH)
• Avoid Dictionary Words: Replace common words with unique combinations (HIGH)

=== AI Generated Password ===
Generated: K9#mP2$vX8@nQ4!z
AI Password Score: 95%
AI Password Strength: SECURE
*/