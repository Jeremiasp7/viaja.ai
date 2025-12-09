package br.com.planejaai.framework.strategy;

public interface LlmStrategy<T> {
  T execute(String input) throws Exception;
}
