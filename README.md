# Aplicación de Gestión de Notas con SQLite

Aplicación móvil desarrollada en Android Studio enfocada en la creación de un sistema completo de gestión de notas (CRUD: Crear, Leer, Actualizar y Eliminar) respaldado por almacenamiento local mediante **SQLite** y una interfaz organizada en pestañas (`Tabs`).

---

## ¿Qué contiene la aplicación? (Características Principales)

1. **Navegación por Pestañas (Tabs):**
   - **Pestaña "Agregar Nota":** Formulario interactivo que permite ingresar el título, la descripción detallada y asociar una imagen de referencia a la nota.
   - **Pestaña "Ver Notas":** Listado general (`ListView`) que despliega todas las notas almacenadas, mostrando estructuradamente el título, la descripción y una imagen miniatura (`ImageView`) por cada registro.
   - **Pestaña "Editar Notas":** Sección interactiva para seleccionar una nota existente de la lista, modificar sus campos (título, descripción o imagen) y actualizarla o eliminarla de forma persistente.

2. **Base de Datos Local (SQLite):**
   - Uso de `SQLiteOpenHelper` para la gestión y creación automática de una base de datos local.
   - Implementación de la tabla `notas` estructurada con los campos: `id` (entero autoincrementable), `título` (texto), `descripción` (texto) e `imagen` (ruta o referencia visual).

3. **Interfaz de Usuario Intuitiva:**
   - Componentes gráficos optimizados para la entrada de datos (`EditText`, `Button`) y visualización dinámica de colecciones (`ListView`, `ImageView`).

---

## ¿Cómo funciona su flujo de uso?

1. **Creación de Notas:**
   - El usuario selecciona la pestaña **Agregar Nota**, redacta el título y la descripción en los campos de texto, adjunta una imagen y presiona el botón de guardar. Los datos se insertan directamente en la tabla SQLite de la aplicación.
   
2. **Consulta y Visualización:**
   - Al cambiar a la pestaña **Ver Notas**, la aplicación ejecuta una consulta a la base de datos local y carga dinámicamente todos los registros a través de un adaptador personalizado, mostrando la información organizada fila por fila en el `ListView`.
   
3. **Modificación y Control (Edición / Eliminación):**
   - Desde la pestaña **Editar Notas**, el usuario selecciona cualquier elemento de la lista para cargar sus datos actuales en el formulario, realizar las modificaciones necesarias en el texto o la imagen, y guardar los cambios o eliminar definitivamente el registro.

---

## Qué tecnologías se usaron para esto

- **Entorno de Desarrollo:** Android Studio
- **Lenguaje:** Java / Kotlin y XML para maquetación de interfaces.
- **Gestión de Datos:** SQLite (`SQLiteOpenHelper`) para el almacenamiento estructurado sin conexión a internet.
- **Componentes de UI:** `TabLayout` (pestañas), `ListView`, `ImageView`, `EditText`, y `Button`.
