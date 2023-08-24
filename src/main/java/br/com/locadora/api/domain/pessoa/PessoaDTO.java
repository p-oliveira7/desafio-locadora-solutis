package br.com.locadora.api.domain.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public record PessoaDTO (
    @NotBlank
    @NotNull
    String nome,
    @NotBlank
    String dataDeNascimento,
    @NotBlank
    @Pattern(regexp = "^([0-9]{3}\\.?){3}-?[0-9]{2}$", message = "O CPF deve estar no formato ###.###.###-## ou sem ponto e hífen")
    String cpf,
    @NotBlank
    String sexo,

    @Pattern(regexp = "\\d{6}", message = "A matrícula deve conter exatamente 6 dígitos")
    String matricula,
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "O número da CNH deve conter exatamente 11 dígitos")
    String numeroCNH
) {
    public Pessoa dtoToEntity() {
        Pessoa pessoa = null;

        if (this.matricula != null) {
            pessoa = new Funcionario();
            ((Funcionario) pessoa).setMatricula(this.matricula);
        } else if (this.numeroCNH != null) {
            pessoa = new Motorista();
            ((Motorista) pessoa).setNumeroCNH(this.numeroCNH);
        } else {
            return null;
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

        pessoa.setNome(this.nome);
        pessoa.setCpf(this.cpf);

        return pessoa;
    }
    private LocalDate converterData(String data) {
        DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .toFormatter(new Locale("pt", "BR"));

        try {
            return LocalDate.parse(data, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido: " + data);
        }
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
