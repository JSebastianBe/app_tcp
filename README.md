# app_tcp 🔌

Aplicación usada para aplicar TCP en tres diferentes escenarios.

## Funcionamiento general 

Se tiene una apicación servidor que al subir el servicio, permite que N aplicaciones cliente se conecten a este. Dependiendo de las opciones del servidor, permitirá la comunicación:
- `clienteA - servidor - clienteA`
- `clienteA - servidor - clientes`
- `clienteA - servidor - clienteB`

![Componentes drawio](https://github.com/user-attachments/assets/2f270f87-2db9-4a89-8d40-f2d7b9f35cce)

La comunicación entre clientes debe darse para dar y recibir archivos.

### Detalle

#### 1. ClienteA - Servidor - ClienteA 1️⃣☞1️⃣

Se establece una instancia de servidor con la opción de conexión única. 

<img width="453" alt="image" src="https://github.com/user-attachments/assets/d267152e-7c91-4e68-bceb-509f191e0aa3">

Esta permitirá que cada uno de los clientes que se conecten se comuniquen cada uno con el servidor y la respuesta de éste, se dara al mismo cliente que se comunicó.

En la aplicación del cliente, se debe utilizar el puerto `12345`, que corresponde al servidor anteriormente subido.

<img width="493" alt="image" src="https://github.com/user-attachments/assets/1c66833e-6f8b-4108-a6d3-ff820601a254">

Al establecer la conexión, el servidor irá informando los clientes que se conecten y las interaciones que tengan estos.

<img width="454" alt="image" src="https://github.com/user-attachments/assets/9f62db33-6782-41df-87ff-3de2fbfa090c">

De esta manera se evidencia la comunicación entre estos:

<img width="962" alt="image" src="https://github.com/user-attachments/assets/38dbef70-bdb3-4c55-aeb3-5bd85be9e11d">

#### 2. ClienteA - Servidor - Clientes 1️⃣☞M

Se establece una instancia de servidor con la opción de enviar a todos los clientes conectados. 

<img width="496" alt="image" src="https://github.com/user-attachments/assets/b67cc694-9256-4646-abdd-e40c2fc778c0">

Esta permitirá que cada uno de los clientes que se conecten se comuniquen cada uno con el servidor y la respuesta de éste, se dará a todos los demás clientes que estén conectados.

En la aplicación del cliente, se debe utilizar el puerto `12346`, que corresponde al servidor anteriormente subido.

<img width="1012" alt="image" src="https://github.com/user-attachments/assets/2329821c-66e4-48d3-8af3-db09aa5bbb89">

Al establecer la conexión, el servidor irá informando los clientes que se conecten y las interaciones que tengan estos.

<img width="494" alt="image" src="https://github.com/user-attachments/assets/eb2e2c3c-4b69-43e0-b9f2-c5cb093eade3">

Al igual que en la opción anterior, a cada cliente que se conecte, se le asigna un identificador único por servidor, para identificar cada uno de ellos dentro del servidor, Por lo que vemos que cada interacción de los clientes con el servidor está seguida de un `Cliente #####`, dónde ##### es el identificador.

En el siguiente ejemplo vemos como el cliente 50176 envía un mensaje de `Hola a todos` es enviado al servidor y seguidamente, enviado a todos los demás clientes conectados

<img width="1579" alt="image" src="https://github.com/user-attachments/assets/f819666d-4ffc-4c97-a1dc-62c5f32d6d61">

#### 3. clienteA - servidor - clienteB A☞B

Se establece una instancia de servidor con la opción de enviar a un cliente en específico. 

<img width="496" alt="image" src="https://github.com/user-attachments/assets/432c035e-8fef-47e7-bb3a-3ccdbbf14d01">

Esta permitirá que cada uno de los clientes que se conecten se comuniquen cada uno con el servidor y la respuesta de éste, se dará el cliente que se especifique.

En la aplicación del cliente, se debe utilizar el puerto `12347`, que corresponde al servidor anteriormente subido.

<img width="997" alt="image" src="https://github.com/user-attachments/assets/59983516-a09e-411f-b16e-47da211d99fb">

Al establecer la conexión, el servidor irá informando los clientes que se conecten y las interaciones que tengan estos.

<img width="492" alt="image" src="https://github.com/user-attachments/assets/587ca715-7cf7-43ee-8648-1045b118c8b8">

Al igual que en la opción anterior, a cada cliente que se conecte, se le asigna un identificador único por servidor, para identificar cada uno de ellos dentro del servidor, Por lo que vemos que cada interacción de los clientes con el servidor está seguida de un `Cliente #####`, dónde ##### es el identificador.

En el siguiente ejemplo vemos como el cliente 50282 envía un mensaje de `Hola` es enviado al servidor y seguidamente, enviado al cliente 50300 que se especificó, esto ocurre sin que el usuario 50290 se entere:

<img width="1555" alt="image" src="https://github.com/user-attachments/assets/7460e5ad-d6f2-4b57-bc8c-2f85af34dfeb">
