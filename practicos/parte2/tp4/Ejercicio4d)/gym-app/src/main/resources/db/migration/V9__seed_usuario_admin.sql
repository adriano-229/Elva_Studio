-- src/main/resources/db/migration/V7__seed_usuario_admin.sql
-- Usuario administrador por defecto para autorizar el envío de mensajes

merge into usuario (id, nombre_usuario, clave, rol, eliminado)
key(id) values
('ADMIN1','admin','{noop}admin123','ADMIN',false);
