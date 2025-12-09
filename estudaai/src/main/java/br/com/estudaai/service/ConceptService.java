package br.com.estudaai.service;

import br.com.planejaai.framework.strategy.ConceptDescriptionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ConceptService extends ConceptDescriptionService {

  public ConceptService(ChatClient.Builder builder) {
    super(builder);
  }

  @Override
  public String generatePrompt(String query) {
    return String.format(
        """
  Sua função é refinar e enriquecer o prompt do usuário para uso em um assistente de estudos.
  Gere uma versão aprimorada do prompt mantendo integramente a intenção do usuário, sem adicionar assuntos não solicitados.

  Ao aprimorar o prompt:
  Mantenha o tema exatamente solicitado pelo usuário (não altere o assunto).

  Aumente clareza, detalhamento e objetividade.

  Reforce que a explicação deve ser correta, didática, coerente e apropriada para estudo.

  Inclua instruções para que a IA forneça exemplos práticos, analogias, exercícios e explicações passo a passo quando apropriado.

  Peça para adaptar a explicação ao nível presumido de estudante iniciante, salvo se o usuário especificar outro nível.

  Não adicione novas intenções, tópicos ou metas que o usuário não pediu.

  Não distorça a pergunta original.

  Não permita fuga de assunto.

 A saída deve ser somente o prompt aprimorado, pronto para ser enviado ao modelo.
  Pergunta do usuário: {userPrompt}

  """,
        query);
  }
}
