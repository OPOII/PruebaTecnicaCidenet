# PruebaTecnicaCidenet

## Instrucciones

1. Clona o descarga el proyecto
2. Abre el proyecto en tu editor favorito, preferiblemente en Intellij Idea
3. Compila el proyecto

## Recomendaciones antes de compilar el proyecto

La base de datos es PostgresSQL, por lo que, se debe de tener una base de datos local en postgress con la siguiente información.

1. Nombre de la base de datos `cidenets`
2. usuario `postgres`
3. contraseña la de su eleccion

igualmente, estos datos puede cambiarlos en el application properties del proyecto, para personalizarlo a su gusto


## Como usasr el proyecto

Para poder ver el API, se debe de acceder a la siguiente url despues de springboot ejecute sin problemas el proyecto.
url=http://localhost:8080/swagger-ui/index.html.
En caso de no tener idea de cuales son los tipos de identificaciones correctas, los paises de trabajo correctos y las areas correctas, hay endpoints llamado `utils`
donde te desplegara los datos que debes de saber para llenar el registro.

La api permite que haya equivocaciones con lo que se especifico anteriormente, por ende, no se rompera si ingresas datos incorrectos. De lo contrario, me gustaria saber cual 
fue el caso en que se registro el break del programa, a modo de retro alimentacion
