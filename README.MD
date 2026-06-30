# BookPoint Chile - Sistema de Arquitectura de Microservicios

---

## 1. Descripción del Contexto

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

El ecosistema de **BookPoint Chile** está compuesto por **14 componentes** (servicios de infraestructura y microservicios de negocio), estructurados bajo una arquitectura limpia y desacoplada:

### Componentes de Infraestructura y Soporte
1.  **`ms-config-server`**: Servidor de configuración centralizado que distribuye las propiedades y perfiles (`.yml` / `.properties`) a todo el ecosistema de forma dinámica.
2.  **`ms-gateway`**: El API Gateway del sistema. Actúa como punto de entrada único, encargándose del enrutamiento de peticiones, balanceo de carga y de la seguridad perimetral.
3.  **`ms-eureka`**: Servidor de descubrimiento de servicios (Service Discovery), permitiendo que los microservicios se registren y localicen entre si de manera dinámica.

### Microservicios del Dominio de Negocio
4.  **`ms-auth`**: Encargado de la seguridad, autenticación, emisión de tokens (JWT) y el control de acceso basado en roles para la plataforma.
5.  **`ms-books`**: Gestión integral del catálogo de libros, autores, editoriales, géneros literarios y precios.
6.  **`ms-customer`**: Administración de perfiles de clientes, direcciones, información de contacto y soporte técnico.
7.  **`ms-inventory`**: Control de stock centralizado y por sucursal física (Concepción, Temuco, La Serena), además del control de alertas de reposición mínima.
8.  **`ms-orders`**: Orquestación y procesamiento de órdenes de compra generadas tanto en canales físicos como web.
9.  **`ms-payments`**: Pasarela y procesamiento de transacciones financieras para el pago de los pedidos de forma segura.
10. **`ms-reviews`**: Motor de interacción de clientes enfocado en capturar reseñas, valoraciones y opiniones de los productos.
11. **`ms-shipping`**: Módulo de logística externa enfocado en la asignación de transportistas, optimización de rutas de distribución y seguimiento del estado del despacho a domicilio.
12. **`ms-stationery`**: Catálogo especializado para la gestión de stock y ventas de artículos de papelería y material educativo escolar.
13. **`ms-transfer`**: Operación logística interna para la gestión, aprobación y rechazo de transferencias de mercadería entre sucursales y la bodega central.
14. **`ms-user`**: Gestión administrativa de los usuarios internos del sistema, asignación de permisos del personal y perfiles (Administradores, Jefes de Sucursal, Logística, Bodega).

---

## 4. Rutas Principales del API Gateway

El microservicio **`ms-gateway`** actúa como el punto de entrada unificado al ecosistema distribuido, escuchando de manera centralizada en el puerto **`8080`**. El enrutamiento perimetral hacia los diferentes microservicios está configurado bajo los siguientes segmentos de rutas y puertos base locales:

| ID del Servicio | Ruta del Predicado (Path) | Puerto Local | Descripción Funcional |
| :--- | :--- | :--- | :--- |
| `usuario-service` | `/api/v1/usuarios/**` | `8081` | Autenticación, control de accesos y perfiles de usuario. |
| `resena-service` | `/api/v1/resenas/**` | `8082` | Gestión de opiniones, valoraciones y feedback de productos. |
| `devolucion-service` | `/api/v1/devoluciones/**` | `8083` | Gestión de cambios y devoluciones por fallas o conformidad. |
| `producto-service` | `/api/v1/productos/**` | `8084` | Catálogo central de libros y artículos escolares/papelería. |
| `inventario-service` | `/api/v1/inventario/**` | `8085` | Control de existencias físicas y alertas de reposición mínima. |
| `venta-service` | `/api/v1/ventas/**` | `8086` | Registro transaccional y facturación de ventas presenciales/web. |
| `pedido-service` | `/api/v1/pedidos/**` | `8087` | Orquestación, carritos de compra e historial de órdenes. |
| `despacho-service` | `/api/v1/despachos/**` | `8088` | Gestión logística de envíos a domicilio y asignación de transportistas. |
| `sucursal-service` | `/api/v1/sucursales/**` | `8089` | Datos informativos y administración física de locales de BookPoint. |
| `proveedor-service` | `/api/v1/proveedores/**` | `8090` | Gestión de editoriales asociadas y órdenes de abastecimiento. |
| `transferencia-service`| `/api/v1/transferencias/**`| `8091` | Movimientos internos de stock entre sucursales y bodega central. |
| `descuento-service` | `/api/v1/descuentos/**` | `8092` | Motor de reglas para cupones y ofertas promocionales. |
| `notificacion-service` | `/api/v1/notificaciones/**`| `8093` | Alertas del sistema a clientes (estados) y a bodega (alertas de stock). |

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

