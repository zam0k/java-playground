package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    private LocacaoService service;
    //fazer a variavel statica faz ela sair do escopo do teste e o junit para de reinicializar ela
    private static Integer i = 0;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new LocacaoService();
        i++;
        System.out.println(i);
    }

    @After
    public void tearDown() {
        //System.out.println("After");
    }

    @BeforeClass
    public static void setupClass() {
        System.out.println("Before Class");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("After Class");
    }

    @Test
    public void teste() throws Exception {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

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
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        service.alugarFilme(usuario, filmes);
    }

    //2) robusta
    // é a forma em que se há mais poder sobre a execução
    @Test
    public void testLocacao_usuarioVazio_robusta() throws FilmeSemEstoqueException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail("Deveria ter lançado uma exceção.");
        } catch(LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    //3) nova
    @Test
    public void testLocacao_filmeVazio_nova() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao

        service.alugarFilme(usuario, null);

    }

    @Test
    public void shouldApply25DiscountToLastMovie() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 5.0),
                new Filme("Filme 2", 3, 5.0),
                new Filme("Filme 3", 5, 5.0)
        );
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        assertThat(locacao.getValor(), is(13.75));
    }

    @Test
    public void shouldApply50DiscountToLastMovie() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 5.0),
                new Filme("Filme 2", 3, 5.0),
                new Filme("Filme 3", 5, 5.0),
                new Filme("Filme 4", 5, 5.0)
        );
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        assertThat(locacao.getValor(), is(17.50));
    }

    @Test
    public void shouldApply75DiscountToLastMovie() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 5.0),
                new Filme("Filme 2", 3, 5.0),
                new Filme("Filme 3", 5, 5.0),
                new Filme("Filme 4", 1, 5.0),
                new Filme("Filme 5", 3, 5.0)
        );
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        assertThat(locacao.getValor(), is(21.25));
    }

    @Test
    public void shouldApply100DiscountToLastMovie() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 5.0),
                new Filme("Filme 2", 3, 5.0),
                new Filme("Filme 3", 5, 5.0),
                new Filme("Filme 4", 1, 5.0),
                new Filme("Filme 5", 3, 5.0),
                new Filme("Filme 6", 5, 5.0)
        );
        Usuario usuario = new Usuario("Usuario 1");

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        assertThat(locacao.getValor(), is(25.0));
    }

}