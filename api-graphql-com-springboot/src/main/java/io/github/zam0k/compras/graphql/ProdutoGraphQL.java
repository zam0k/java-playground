package io.github.zam0k.compras.graphql;

import io.github.zam0k.compras.graphql.inputs.ProdutoInput;
import io.github.zam0k.compras.model.Produto;
import io.github.zam0k.compras.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProdutoGraphQL {
  private final ProdutoService service;

  @QueryMapping
  public Produto produto(@Argument Long id) {
    return service.findById(id);
  }

  @QueryMapping
  public List<Produto> produtos() {
    return service.findAll();
  }

  @MutationMapping
  public Produto saveProduto(@Argument("produto") ProdutoInput input) {
    return service.save(input);
  }

  @MutationMapping
  public Boolean deleteProduto(Long id) {
    return service.deleteById(id);
  }
}
