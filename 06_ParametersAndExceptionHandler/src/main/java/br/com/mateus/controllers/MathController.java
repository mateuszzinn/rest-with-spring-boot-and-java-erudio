package br.com.mateus.controllers;

import br.com.mateus.exception.UnsupportedMathOperationException;
import br.com.mateus.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.mateus.utils.Util.ConvertToDouble;
import static br.com.mateus.utils.Util.IsNumeric;

@RestController
public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/soma/{n1}/{n2}")
    public double soma(@PathVariable(value = "n1") String n1, @PathVariable(value = "n2") String n2) throws Exception{
        if(!Util.IsNumeric(n1) || !Util.IsNumeric(n2)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return ConvertToDouble(n1) + ConvertToDouble(n2);
    }

    @GetMapping("/subtracao/{n1}/{n2}")
    public double subtracao(@PathVariable(value = "n1") String n1, @PathVariable(value = "n2") String n2) throws Exception{
        if(!Util.IsNumeric(n1) || !Util.IsNumeric(n2)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Util.ConvertToDouble(n1) - Util.ConvertToDouble(n2);
    }

    @GetMapping("/multiplicacao/{n1}/{n2}")
    public double multiplicacao(@PathVariable(value = "n1") String n1, @PathVariable(value = "n2") String n2) throws Exception{
        if(!Util.IsNumeric(n1) || !Util.IsNumeric(n2)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Util.ConvertToDouble(n1) * Util.ConvertToDouble(n2);
    }

    @GetMapping("/divisao/{n1}/{n2}")
    public double divisao(@PathVariable(value = "n1") String n1, @PathVariable(value = "n2") String n2) throws Exception{
        if(!Util.IsNumeric(n1) || !Util.IsNumeric(n2)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Util.ConvertToDouble(n1) / Util.ConvertToDouble(n2);
    }

    @GetMapping("/media/{n1}/{n2}")
    public double media(@PathVariable(value = "n1") String n1, @PathVariable(value = "n2") String n2) throws Exception{
        if(!Util.IsNumeric(n1) || !Util.IsNumeric(n2)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return (Util.ConvertToDouble(n1) + Util.ConvertToDouble(n2)) / 2;
    }

    @GetMapping("/raiz/{n1}")
    public double raiz(@PathVariable(value = "n1") String n1) throws Exception{
        if(!Util.IsNumeric(n1)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Math.sqrt(Util.ConvertToDouble(n1));
    }

}
