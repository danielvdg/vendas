package br.com.vendas.controller;

import br.com.vendas.exception.PedidoNaoEncontradoException;
import br.com.vendas.exception.RegraNegocioException;
import br.com.vendas.model.entity.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApllicationControllerAdvice {

  @ExceptionHandler(RegraNegocioException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
      String mensagemErro = ex.getMessage();
      return new ApiErrors(mensagemErro);
  }
  @ExceptionHandler(PedidoNaoEncontradoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrors handleRegraNegocioException(PedidoNaoEncontradoException ex) {
    String mensagemErro = ex.getMessage();
    return new ApiErrors(mensagemErro);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public  ApiErrors handleMethodNotValidExcpetion(MethodArgumentNotValidException ex) {
    List<String> erros = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(erro -> erro.getDefaultMessage())
            .collect(Collectors.toList());
    return new ApiErrors(erros.toString());
  }
}
