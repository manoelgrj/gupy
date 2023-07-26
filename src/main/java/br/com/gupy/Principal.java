package br.com.gupy;

import br.com.gupy.model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private List<Funcionario> funcionarios = new ArrayList<>();

    public static void execute() {
        Principal principal = new Principal();
        principal.inserirFuncionarios();
        principal.removerFuncionario("João");
        principal.imprimirFuncionarios();
        principal.aumentarSalarios(0.10);
        principal.agruparFuncionariosPorFuncao();
        principal.imprimirAniversariantesMes(10, 12);
        principal.imprimirFuncionarioMaisVelho();
        principal.imprimirFuncionariosOrdenadosPorNome();
        principal.imprimirTotalSalarios();
        principal.imprimirQuantidadeSalariosMinimos();
    }

    public void inserirFuncionarios() {
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
    }

    public void removerFuncionario(String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    public void imprimirFuncionarios() {
        System.out.println("Lista de funcionários:");
        funcionarios.forEach(System.out::println);
    }

    public void aumentarSalarios(double percentualAumento) {
        BigDecimal percentual = BigDecimal.valueOf(1 + percentualAumento);
        funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(percentual)));
    }

    public void agruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            listaFuncionarios.forEach(System.out::println);
            System.out.println();
        });
    }

    public void imprimirAniversariantesMes(int... meses) {
        System.out.println("\nFuncionários que fazem aniversário no mês " + Arrays.toString(meses) + ":");
        funcionarios.stream()
                .filter(funcionario -> Arrays.stream(meses).anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes))
                .forEach(System.out::println);
    }

    public void imprimirFuncionarioMaisVelho() {
        System.out.println("\nFuncionário mais velho:");
        Comparator<Funcionario> comparadorIdade = Comparator.comparing(funcionario -> funcionario.getDataNascimento());
        Funcionario funcionarioMaisVelho = funcionarios.stream().min(comparadorIdade).orElse(null);
        if (funcionarioMaisVelho != null) {
            int idade = calcularIdade(funcionarioMaisVelho.getDataNascimento());
            System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade + " anos");
        }
    }

    public void imprimirFuncionariosOrdenadosPorNome() {
        System.out.println("\nFuncionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);
    }

    public void imprimirTotalSalarios() {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + formatarValor(totalSalarios));
    }

    public void imprimirQuantidadeSalariosMinimos() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantidade de salários mínimos por funcionário:");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.println(funcionario.getNome() + ": " + quantidadeSalariosMinimos);
        });
    }

    private int calcularIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return hoje.getYear() - dataNascimento.getYear();
    }

    private String formatarValor(BigDecimal valor) {
        return String.format("%,.2f", valor);
    }
}
