package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void teste() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        //acao

        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao

        //      assertEquals(5.0, locacao.getValor(), 0.01);
        //ler sobre fluent interface:
        //      assertThat(locacao.getValor(), is(equalTo(5.0)));
        //usando rule:
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
    }

    //formas de tratar um teste que espera receber uma exceção

    //1) elegante
    // para usar essa, devemos ter a garantia de que a exceção é bem específica
    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacao_filmeSemEstoque_elegante() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //acao

        service.alugarFilme(usuario, filme);
    }

    //2) robusta
    // é a forma em que se há mais poder sobre a execução 
    @Test
    public void testLocacao_usuarioVazio_robusta() throws FilmeSemEstoqueException {
        //cenario
        LocacaoService service = new LocacaoService();
        Filme filme = new Filme("Filme 1", 1, 5.0);

        //acao
        try {
            service.alugarFilme(null, filme);
            Assert.fail("Deveria ter lançado uma exceção.");
        } catch(LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    //3) nova
    @Test
    public void testLocacao_filmeVazio_nova() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao

        service.alugarFilme(usuario, null);

    }


}
