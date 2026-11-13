package com.example.springloja;

import com.example.springloja.controller.ProdutoController;
import com.example.springloja.controller.VendedorController;
import com.example.springloja.entities.Cliente;
import com.example.springloja.entities.Produto;
import com.example.springloja.entities.Venda;
import com.example.springloja.entities.Vendedor;
import com.example.springloja.repositories.ClienteRepository;
import com.example.springloja.repositories.VendaRepository;
import com.example.springloja.services.ClienteService;
import com.example.springloja.services.ProdutoService;
import com.example.springloja.services.StorageService;
import com.example.springloja.services.VendaService;
import com.example.springloja.services.VendedorService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrudFrontendTests {

    @Test
    void cadastrarVendedorComFotoNaoDeveLancarNullPointerException() {
        VendedorController controller = new VendedorController();
        VendedorService service = mock(VendedorService.class);
        StorageService storage = mock(StorageService.class);
        ReflectionTestUtils.setField(controller, "service", service);
        ReflectionTestUtils.setField(controller, "storageService", storage);

        MultipartFile foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);
        when(storage.salvarArquivo(foto)).thenReturn("/uploads/foto.jpg");

        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Teste");

        assertEquals("Vendedor Teste criado com sucesso!", controller.criar(vendedor, foto));
        assertEquals("/uploads/foto.jpg", vendedor.getFoto());
        verify(service).salvar(vendedor);
    }

    @Test
    void cadastroDeProdutoSemFotoAceitaMetodoJson() {
        ProdutoController controller = new ProdutoController();
        ProdutoService service = mock(ProdutoService.class);
        ReflectionTestUtils.setField(controller, "service", service);

        Produto produto = new Produto();
        produto.setNome("Mouse");

        assertEquals("Produto Mouse criado com sucesso!", controller.criarJson(produto));
        verify(service).salvar(produto);
    }

    @Test
    void atualizarClienteMantemIdEAtualizaCampos() {
        ClienteRepository repository = mock(ClienteRepository.class);
        ClienteService service = new ClienteService();
        ReflectionTestUtils.setField(service, "repository", repository);

        Cliente existente = new Cliente();
        existente.setId(7L);
        existente.setNome("Antigo");
        when(repository.findById(7L)).thenReturn(Optional.of(existente));

        Cliente recebido = new Cliente();
        recebido.setId(7L);
        recebido.setNome("Novo");
        recebido.setRenda(2000f);
        service.atualizar(recebido);

        assertEquals("Novo", existente.getNome());
        assertEquals(2000f, existente.getRenda());
        verify(repository).save(existente);
    }

    @Test
    void clienteInexistenteRetorna404() {
        ClienteRepository repository = mock(ClienteRepository.class);
        ClienteService service = new ClienteService();
        ReflectionTestUtils.setField(service, "repository", repository);
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Cliente recebido = new Cliente();
        recebido.setId(999L);
        recebido.setNome("Novo");
        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> service.atualizar(recebido));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void atualizarVendaPreservaIdERecebeValorClienteEVendedor() {
        VendaRepository repository = mock(VendaRepository.class);
        VendaService service = new VendaService();
        ReflectionTestUtils.setField(service, "repository", repository);

        Venda existente = new Venda();
        existente.setId(15L);
        when(repository.findById(15L)).thenReturn(Optional.of(existente));

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Vendedor vendedor = new Vendedor();
        vendedor.setId(2L);

        Venda recebido = new Venda();
        recebido.setId(15L);
        recebido.setData(LocalDate.of(2026, 11, 13));
        recebido.setVenda(350f);
        recebido.setCliente(cliente);
        recebido.setVendedor(vendedor);

        service.atualizar(recebido);

        assertEquals(15L, existente.getId());
        assertEquals(350f, existente.getVenda());
        assertEquals(cliente, existente.getCliente());
        assertEquals(vendedor, existente.getVendedor());
        verify(repository).save(existente);
    }
}
