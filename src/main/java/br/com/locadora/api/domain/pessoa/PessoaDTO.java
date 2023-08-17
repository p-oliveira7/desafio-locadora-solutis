package br.com.locadora.api.domain.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record PessoaDTO (
    @NotBlank
    String nome,
    @NotBlank
    String dataDeNascimento,
    @NotBlank
    @Pattern.List({
            @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato ###.###.###-##"),
            @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números")
    })
    String cpf,
    @NotBlank
    String sexo,
    String matricula,
    String numeroCNH
) {
    public Pessoa converterDTOparaEntidade() {
        Pessoa pessoa;

        if (this.matricula != null) {
            pessoa = new Funcionario();
        } else if (this.numeroCNH != null) {
            pessoa = new Motorista();
        } else {
            pessoa = new Pessoa();
        }

        try {
            LocalDate dataDeNascimento = converterData(this.dataDeNascimento);
            pessoa.setDataDeNascimento(dataDeNascimento);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data de nascimento inválida: " + this.dataDeNascimento);
        }

        try {
            pessoa.setSexo(converterSexo());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sexo inválido: " + this.sexo);
        }

        return pessoa;
    }
    private LocalDate converterData(String data) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("ddMMyyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yy"),
                DateTimeFormatter.ofPattern("dd.MM.yy"),
                DateTimeFormatter.ofPattern("dd-MM-yy"),
                DateTimeFormatter.ofPattern("ddMMyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(data, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException("Formato de data inválido: " + data);
    }

    private Sexo converterSexo() {
        String sexoUpperCase = this.sexo.toUpperCase();

        if (sexoUpperCase.equals("M") || sexoUpperCase.equals("MASCULINO")) {
            return Sexo.MASCULINO;
        } else if (sexoUpperCase.equals("F") || sexoUpperCase.equals("FEMININO")) {
            return Sexo.FEMININO;
        } else {
            throw new IllegalArgumentException("Sexo inválido: " + this.sexo);
        }
    }
    @Override
    public String toString() {
        return "PessoaDTO[" +
                "nome='" + nome + '\'' +
                ", dataDeNascimento='" + dataDeNascimento + '\'' +
                ", cpf='" + cpf + '\'' +
                ", sexo='" + sexo + '\'' +
                ", matricula='" + matricula + '\'' +
                ", numeroCNH='" + numeroCNH + '\'' +
                ']';
    }
}
