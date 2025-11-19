package br.com.levelup.api.erro;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ❌ Erros de validação de @Valid em @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<CampoErro> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new CampoErro(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());

        ErroResposta body = new ErroResposta(
                "Erro de validação",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now().toString(),
                campos
        );

        return ResponseEntity.badRequest().body(body);
    }

    // ❌ Erros de validação em @RequestParam, @PathVariable, etc (ConstraintViolation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResposta> handleConstraintViolation(ConstraintViolationException ex) {

        List<CampoErro> campos = ex.getConstraintViolations()
                .stream()
                .map(v -> new CampoErro(
                        v.getPropertyPath().toString(),
                        v.getMessage()
                ))
                .collect(Collectors.toList());

        ErroResposta body = new ErroResposta(
                "Erro de validação",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now().toString(),
                campos
        );

        return ResponseEntity.badRequest().body(body);
    }

    // ⚠️ Qualquer RuntimeException que escapar (ex: "Usuário não encontrado")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> handleRuntime(RuntimeException ex) {

        HttpStatus status;
        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("não encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        ErroResposta body = new ErroResposta(
                ex.getMessage(),
                status.value(),
                Instant.now().toString(),
                null
        );

        return ResponseEntity.status(status).body(body);
    }

    // =========================
    //  DTOs internos do handler
    // =========================

    public static class ErroResposta {
        private String mensagem;
        private int status;
        private String timestamp;
        private List<CampoErro> erros;

        public ErroResposta(String mensagem, int status, String timestamp, List<CampoErro> erros) {
            this.mensagem = mensagem;
            this.status = status;
            this.timestamp = timestamp;
            this.erros = erros;
        }

        public String getMensagem() {
            return mensagem;
        }

        public int getStatus() {
            return status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public List<CampoErro> getErros() {
            return erros;
        }
    }

    public static class CampoErro {
        private String campo;
        private String mensagem;

        public CampoErro(String campo, String mensagem) {
            this.campo = campo;
            this.mensagem = mensagem;
        }

        public String getCampo() {
            return campo;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
