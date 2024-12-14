# Práctica db4o de ADAT
Adaptación de la práctica anterior, pero con un sistema de bases de datos obsoleto conocido como db4o.

## Compilación
Primero necesitarás una copia de DB4O 8.0.276.16149, puedes descargarla [aquí](https://www.dropbox.com/scl/fi/e4fmptqon6mmpbtrgor5e/db4o-8.0.276.16149-java.zip?e=1&file_subpath=%2Fdb4o-8.0%2Flib%2Fdb4o-8.0.276.16149-all-java5.jar&rlkey=j6ylx137uzydpb6kqhhsf3j5p&st=1mptigar&dl=0).

Luego tendrás que instalarla en tu repositorio local de maven:
```shell
mvn install:install-file -Dfile=/path/to/db4o-8.0.276.16149-all-java5.jar \
    -DgroupId=com.db4o \
    -DartifactId=db4o \
    -Dversion=8.0.276 \
    -Dpackaging=jar
```
Donde `/path/to/db4o-8.0.276.16149-all-java5.jar` es donde reside el `.jar` descargado.

Finalmente compila:
```shell
mvn compile
```

## Ejecución
```shell
mvn exec:java
```

## Test
```shell
mvn test
```

## Autor
Aimar Ibarra