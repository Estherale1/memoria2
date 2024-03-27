package com.example.demo.modulologin.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class Logs {

    private static final String ARCHIVO_LOG = "logs.txt";
    private static final String DIRECTORIO = "/home/albertalv/Documentos/";

    public void devolverLog(String usuario, String modulo) throws java.io.IOException{
        LocalDateTime hora = LocalDateTime.now();
        String logs = hora + usuario + modulo;
        Files.write(Paths.get(DIRECTORIO + ARCHIVO_LOG),
                    (logs + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
    }
}
