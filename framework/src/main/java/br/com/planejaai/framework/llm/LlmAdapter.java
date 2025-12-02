package br.com.planejaai.framework.llm;

import org.springframework.core.ParameterizedTypeReference;

/**
 * Adapter interface to abstract LLM/chat client usage.
 */
public interface LlmAdapter {

    String generate(String prompt);
    <T> T generateEntity(String prompt, ParameterizedTypeReference<T> typeRef);
}
