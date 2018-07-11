package com.felipe.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipe.cursomc.domain.Categoria;
import com.felipe.cursomc.domain.Cidade;
import com.felipe.cursomc.domain.Cliente;
import com.felipe.cursomc.domain.Endereco;
import com.felipe.cursomc.domain.Estado;
import com.felipe.cursomc.domain.ItemPedido;
import com.felipe.cursomc.domain.Pagamento;
import com.felipe.cursomc.domain.PagamentoComBoleto;
import com.felipe.cursomc.domain.PagamentoComCartao;
import com.felipe.cursomc.domain.Pedido;
import com.felipe.cursomc.domain.Produto;
import com.felipe.cursomc.domain.enums.EstadoPagamento;
import com.felipe.cursomc.domain.enums.TipoCliente;
import com.felipe.cursomc.repositories.CategoriaRepository;
import com.felipe.cursomc.repositories.CidadeRepository;
import com.felipe.cursomc.repositories.ClienteRepository;
import com.felipe.cursomc.repositories.EnderecoRepository;
import com.felipe.cursomc.repositories.EstadoRepository;
import com.felipe.cursomc.repositories.ItemPedidoRepository;
import com.felipe.cursomc.repositories.PagamentoRepository;
import com.felipe.cursomc.repositories.PedidoRepository;
import com.felipe.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Informatica");
		Categoria c2 = new Categoria(null, "Escritorio");
		Categoria c3 = new Categoria(null, "Cama mesa e banho");
		Categoria c4 = new Categoria(null, "Eletronicos");
		Categoria c5 = new Categoria(null, "Jardinagem");
		Categoria c6 = new Categoria(null, "Perfumaria");
		Categoria c7 = new Categoria(null, "Decoração");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00 );
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		c2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(c1));
		p2.getCategorias().addAll(Arrays.asList(c1,c2));
		p3.getCategorias().addAll(Arrays.asList(c1));
		
		
		categoriaRepository.save(Arrays.asList(c1,c2,c3,c4,c5,c6,c7));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
		
		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");
		
		Cidade cit1 = new Cidade(null, "Uberlandia", e1);
		Cidade cit2 = new Cidade(null, "São Paulo", e2);
		Cidade cit3 = new Cidade(null, "Camapinas", e2);
		
		e1.getCidades().addAll(Arrays.asList(cit1));
		e2.getCidades().addAll(Arrays.asList(cit2, cit3));
		
		estadoRepository.save(Arrays.asList(e1,e2));
		cidadeRepository.save(Arrays.asList(cit1, cit2,cit3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "Marias@gmail.com","23010433808", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("24666824", "21711100"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 230", "Jardim", "38220834", cli1, cit1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777834", cli1, cit2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 9:35"), cli1, end2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.save(Arrays.asList(ped1,ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.save(Arrays.asList(ip1, ip2,ip3));
		

	}
}
