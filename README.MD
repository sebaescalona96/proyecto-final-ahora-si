# BookPoint Chile - Sistema de Arquitectura de Microservicios

---

## 1. Descripción del Contexto / Dominio

**BookPoint Chile** es una empresa nacional dedicada a la venta de libros, artículos de papelería y material educativo. Nacida originalmente como una librería independiente en Concepción, ha expandido sus operaciones con sucursales estratégicas en Temuco y La Serena. 

Debido al crecimiento exponencial de la demanda en tiendas físicas y canales digitales, el sistema monolítico original de la empresa comenzó a presentar problemas críticos de escalabilidad: lentitud en procesos de negocio, desfases y desactualización en el stock de inventario, y demoras significativas en la gestión logística y despachos.

Para solventar estas debilidades, modernizar la plataforma y asegurar la alta disponibilidad, se diseñó e implementó una **solución distribuida basada en una Arquitectura de Microservicios**. Este ecosistema desacopla las operaciones clave del negocio (ventas, stock centralizado, logística inter-sucursales, interacción con el cliente y pasarelas de pago), garantizando una infraestructura elástica, tolerante a fallos y capaz de soportar la carga transaccional del negocio.

---

## 2. Integrantes del Equipo

* **Sebastián Escalona**
* **Cristóbal Olivares**
* **Flavio Bustos**

---

## 3. Listado de Microservicios e Infraestructura

El ecosistema de **BookPoint Chile** está compuesto por **13 componentes** independientes (1 servicio de infraestructura perimetral y 12 microservicios de negocio), estructurados bajo un enrutamiento desacoplado:

### Componentes de Infraestructura
1.  **`gateway-service`**: El API Gateway del sistema (escuchando en el puerto `8080`). Actúa como punto de entrada único para los clientes, encargándose del enrutamiento estático de peticiones y de la seguridad perimetral.

### Microservicios del Dominio de Negocio
2.  **`usuario-service`**: Autenticación, control de accesos, gestión de permisos del personal y perfiles de usuario.
3.  **`resena-service`**: Gestión de opiniones, valoraciones y feedback de productos por parte de los clientes.
4.  **`devolucion-service`**: Gestión de cambios y devoluciones por fallas o conformidad de productos.
5.  **`producto-service`**: Catálogo centralizado de libros y artículos escolares/papelería.
6.  **`inventario-service`**: Control de existencias físicas en bodega y sucursales, además de alertas de reposición mínima.
7.  **`venta-service`**: Registro transaccional, boletas y facturación de ventas presenciales y web.
8.  **`pedido-service`**: Orquestación, carritos de compra e historial de órdenes de clientes web.
9.  **`despacho-service`**: Gestión logística de envíos a domicilio y asignación de transportistas.
10. **`sucursal-service`**: Datos informativos y administración física de locales de BookPoint (Concepción, Temuco, La Serena).
11. **`proveedor-service`**: Gestión de editoriales asociadas y órdenes de abastecimiento de mercadería.
12. **`transferencia-service`**: Movimientos internos de stock y solicitudes de reposición entre sucursales y la bodega central.
13. **`descuento-service`**: Motor de reglas para cupones y ofertas promocionales.
14. **`notificacion-service`**: Alertas del sistema a clientes (estados de compra) y a personal (alertas de stock bajo).

---

## 4. Rutas Principales del API Gateway

El microservicio **`gateway-service`** actúa como el punto de entrada unificado al ecosistema distribuido, escuchando de manera centralizada en el puerto **`8080`**. El enrutamiento hacia los diferentes microservicios está configurado de forma estática bajo los siguientes segmentos de rutas y puertos locales:

| ID del Servicio | Ruta del Predicado (Path) | Puerto Local | Descripción Funcional |
| :--- | :--- | :--- | :--- |
| `usuario-service` | `/api/v1/usuarios/**` | `8081` | Gestión de usuarios y seguridad. |
| `resena-service` | `/api/v1/resenas/**` | `8082` | Gestión de opiniones y calificaciones. |
| `devolucion-service` | `/api/v1/devoluciones/**` | `8083` | Gestión de devoluciones y cambios. |
| `producto-service` | `/api/v1/productos/**` | `8084` | Catálogo de libros y papelería. |
| `inventario-service` | `/api/v1/inventario/**` | `8085` | Control de stock y existencias. |
| `venta-service` | `/api/v1/ventas/**` | `8086` | Flujo de venta y facturación. |
| `pedido-service` | `/api/v1/pedidos/**` | `8087` | Carrito de compras y órdenes. |
| `despacho-service` | `/api/v1/despachos/**` | `8088` | Despachos y logística de envíos. |
| `sucursal-service` | `/api/v1/sucursales/**` | `8089` | Administración de tiendas físicas. |
| `proveedor-service` | `/api/v1/proveedores/**` | `8090` | Editoriales y órdenes de compra. |
| `transferencia-service`| `/api/v1/transferencias/**`| `8091` | Movimientos internos de mercadería.|
| `descuento-service` | `/api/v1/descuentos/**` | `8092` | Cupones y promociones. |
| `notificacion-service` | `/api/v1/notificaciones/**`| `8093` | Sistema de alertas por correo/push. |

---

## 5. Documentación de Endpoints (Swagger / OpenAPI)

Toda la API se encuentra documentada utilizando OpenAPI 3 y la interfaz interactiva de Swagger UI. Para facilitar las pruebas, los accesos se unifican y exponen a través del puerto base del **API Gateway (`8080`)** utilizando los prefijos correspondientes:

| Microservicio | URL de Documentación Swagger (Entorno Local) |
| :--- | :--- |
| `usuario-service` | [http://localhost:8080/api/v1/usuarios/swagger-ui/index.html](http://localhost:8080/api/v1/usuarios/swagger-ui/index.html) |
| `resena-service` | [http://localhost:8080/api/v1/resenas/swagger-ui/index.html](http://localhost:8080/api/v1/resenas/swagger-ui/index.html) |
| `devolucion-service` | [http://localhost:8080/api/v1/devoluciones/swagger-ui/index.html](http://localhost:8080/api/v1/devoluciones/swagger-ui/index.html) |
| `producto-service` | [http://localhost:8080/api/v1/productos/swagger-ui/index.html](http://localhost:8080/api/v1/productos/swagger-ui/index.html) |
| `inventario-service` | [http://localhost:8080/api/v1/inventario/swagger-ui/index.html](http://localhost:8080/api/v1/inventario/swagger-ui/index.html) |
| `venta-service` | [http://localhost:8080/api/v1/ventas/swagger-ui/index.html](http://localhost:8080/api/v1/ventas/swagger-ui/index.html) |
| `pedido-service` | [http://localhost:8080/api/v1/pedidos/swagger-ui/index.html](http://localhost:8080/api/v1/pedidos/swagger-ui/index.html) |
| `despacho-service` | [http://localhost:8080/api/v1/despachos/swagger-ui/index.html](http://localhost:8080/api/v1/despachos/swagger-ui/index.html) |
| `sucursal-service` | [http://localhost:8080/api/v1/sucursales/swagger-ui/index.html](http://localhost:8080/api/v1/sucursales/swagger-ui/index.html) |
| `proveedor-service` | [http://localhost:8080/api/v1/proveedores/swagger-ui/index.html](http://localhost:8080/api/v1/proveedores/swagger-ui/index.html) |
| `transferencia-service`| [http://localhost:8080/api/v1/transferencias/swagger-ui/index.html](http://localhost:8080/api/v1/transferencias/swagger-ui/index.html) |
| `descuento-service` | [http://localhost:8080/api/v1/descuentos/swagger-ui/index.html](http://localhost:8080/api/v1/descuentos/swagger-ui/index.html) |
| `notificacion-service` | [http://localhost:8080/api/v1/notificaciones/swagger-ui/index.html](http://localhost:8080/api/v1/notificaciones/swagger-ui/index.html) |

---

## 6. Instrucciones Básicas de Ejecución

Para iniciar el ecosistema de microservicios de manera correcta en un entorno de desarrollo local, siga estos pasos:

### Paso 1: Clonar el Repositorio
```bash
git clone [https://github.com/sebaescalona96/proyecto-final-ahora-si.git](https://github.com/sebaescalona96/proyecto-final-ahora-si.git)
cd proyecto-final-ahora-si

message.txt
8 KB
