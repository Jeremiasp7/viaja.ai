package br.com.estudaai.service;

import br.com.estudaai.entity.StudyUser;
import br.com.estudaai.repository.UserRepository;
import br.com.planejaai.framework.strategy.ConceptDescriptionService;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ConceptService extends ConceptDescriptionService {

    private final UserRepository userRepository;
    private final ChatClient chatClient;

    public ConceptService(ChatClient chatClient, UserRepository userRepository) {
        super(chatClient);
        this.chatClient = chatClient;
        this.userRepository = userRepository;
    }

    public String recommendConcept(String query, UUID userId) {
        StudyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        String personalizedPrompt = generatePromptWithContext(query, user);

        return chatClient.prompt()
                .user(personalizedPrompt)
                .call()
                .content();
    }

    private String generatePromptWithContext(String query, StudyUser user) {
        String educationLevel = (user.getEducation() != null && !user.getEducation().isEmpty()) 
                ? user.getEducation() 
                : "estudante iniciante";

        return String.format(
                """
                Sua função é refinar e enriquecer o prompt do usuário para uso em um assistente de estudos.
                Gere uma versão aprimorada do prompt mantendo integramente a intenção do usuário.

                CONTEXTO DO ALUNO:
                O aluno possui o seguinte nível de escolaridade: %s.
                Adapte toda a explicação, exemplos e analogias para este nível específico.

                Ao aprimorar o prompt:
                - Mantenha o tema exatamente solicitado.
                - Aumente clareza, detalhamento e objetividade.
                - Inclua instruções para que a IA forneça exemplos práticos.
                
                Pergunta do usuário: %s
                """,
                educationLevel,
                query);
    }
    
    @Override
    public String generatePrompt(String query) {
        return generatePromptWithContext(query, new StudyUser()); 
    }
}
