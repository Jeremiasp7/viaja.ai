package br.com.planejaai.framework.service.interfaces;

import br.com.planejaai.framework.dto.GenericUsersPreferencesDto;
import br.com.planejaai.framework.entity.GenericUsersPreferencesEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;

import java.util.UUID;

public interface UserPreferencesService {

    GenericUsersPreferencesEntity atualizarPreferencias(UUID userId, GenericUsersPreferencesDto dto) throws UsuarioNaoEncontradoException;

    GenericUsersPreferencesEntity buscarPreferenciasDoUsuario(UUID userId) throws UsuarioNaoEncontradoException;
}
