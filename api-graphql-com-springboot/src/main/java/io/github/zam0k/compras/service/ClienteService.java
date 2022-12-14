package io.github.zam0k.compras.service;

import io.github.zam0k.compras.graphql.inputs.ClienteInput;
import io.github.zam0k.compras.model.Cliente;
import io.github.zam0k.compras.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
  private final ClienteRepository repository;

  public Cliente findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Cliente> findAll() {
    return repository.findAll();
  }

  @Transactional
  public Cliente save(ClienteInput input) {
    ModelMapper m = new ModelMapper();
    Cliente c = m.map(input, Cliente.class);
    return repository.save(c);
  }

  @Transactional
  public Boolean deleteById(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}
