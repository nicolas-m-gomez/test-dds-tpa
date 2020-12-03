
    create table Etiquetas (
        Egreso_egreso_id integer not null,
        etiquetas varchar(255)
    )

    create table Importes (
        importe_id integer not null auto_increment,
        precio_unitario decimal(19,2),
        moneda_id varchar(255) not null,
        primary key (importe_id)
    )

    create table Items (
        id integer not null auto_increment,
        cantidad integer,
        descripcion varchar(255),
        importe_importe_id integer,
        presupuesto_item_id integer,
        primary key (id)
    )

    create table categorias (
        categoria_id integer not null auto_increment,
        nombre varchar(255),
        primary key (categoria_id)
    )

    create table categorias_reglas_negocio (
        categorias_categoria_id integer not null,
        reglasNegocio_regla_negocio_id integer not null
    )

    create table direcciones (
        direccion_id integer not null auto_increment,
        direccion_altura integer,
        direccion_calle varchar(255),
        direccion_codigo_postal varchar(255),
        direccion_piso varchar(255),
        ubicacion_ciudad varchar(255),
        ubicacion_pais varchar(255),
        ubicacion_provincia varchar(255),
        primary key (direccion_id)
    )

    create table doc_comerciales (
        doc_comercial_codigo varchar(255) not null,
        doc_comercial_tipo varchar(255),
        primary key (doc_comercial_codigo)
    )

    create table egresos (
        egreso_id integer not null auto_increment,
        estado varchar(255),
        fecha date,
        requierePresupuesto bit,
        docuComercial_doc_comercial_codigo varchar(255),
        medioPago_medio_pago_id integer,
        proveedor_proveedor_id integer,
        primary key (egreso_id)
    )

    create table egresos_Items (
        egresos_egreso_id integer not null,
        itemsEgreso_id integer not null
    )

    create table egresos_usuarios (
        egresos_egreso_id integer not null,
        revisores_usuario_id integer not null
    )

    create table entidades_base (
        entidad_base_descripcion varchar(255),
        entidad_base_permite_agregarse bit,
        organizacion integer not null,
        entidad_juridica integer,
        primary key (organizacion)
    )

    create table entidades_juridicas (
        entidad_juridica_maximo_entidades_base integer,
        entidad_juridica_codigo_igj integer,
        entidad_juridica_ciut integer,
        entidad_juridica_razon_social varchar(255),
        entidad_juridica_tipo_empresa varchar(255),
        organizacion integer not null,
        entidad_juridica_direccion integer,
        primary key (organizacion)
    )

    create table medios_de_pago (
        medio_pago_id integer not null auto_increment,
        medio_pago_codigo varchar(255),
        medio_pago_tipo varchar(255),
        primary key (medio_pago_id)
    )

    create table mensajes (
        notificacion_id integer not null,
        mensajes varchar(255)
    )

    create table monedas (
        moneda_id varchar(255) not null,
        moneda_descripcion varchar(255),
        moneda_simbolo varchar(255),
        primary key (moneda_id)
    )

    create table notificaciones (
        notificacion_id integer not null auto_increment,
        notificacion_fecha date,
        egreso_id integer,
        usuario_id integer,
        primary key (notificacion_id)
    )

    create table organizaciones (
        organizacion_id integer not null auto_increment,
        organizacion_monto_max_egreso double precision,
        organizacion_nombre_ficticio varchar(255),
        categoria_id integer not null,
        primary key (organizacion_id)
    )

    create table organizaciones_egresos (
        organizaciones_organizacion_id integer not null,
        egresos_egreso_id integer not null
    )

    create table presupuestos (
        presupuesto_id integer not null auto_increment,
        doc_comercial_codigo varchar(255),
        proveedor_id integer,
        egreso_id integer,
        primary key (presupuesto_id)
    )

    create table proveedores (
        proveedor_id integer not null auto_increment,
        proveedor_denominacion varchar(255),
        direccion_id integer,
        primary key (proveedor_id)
    )

    create table reglas_negocio (
        regla_negocio_id integer not null auto_increment,
        primary key (regla_negocio_id)
    )

    create table usuarios (
        usuario_id integer not null auto_increment,
        usuario_pass varchar(255),
        usuario_tipo varchar(255),
        usuario varchar(255),
        primary key (usuario_id)
    )

    alter table egresos_Items 
        add constraint UK_gk57jvogfns35pnvlxuf9eoto  unique (itemsEgreso_id)

    alter table egresos_usuarios 
        add constraint UK_s1oppuctqllu0qxywb75rk2rb  unique (revisores_usuario_id)

    alter table organizaciones_egresos 
        add constraint UK_a3k8ctqply3h0nhv69vt8sovw  unique (egresos_egreso_id)

    alter table Etiquetas 
        add constraint FK_g2c16ii3fbhj29kng4enrtges 
        foreign key (Egreso_egreso_id) 
        references egresos (egreso_id)

    alter table Importes 
        add constraint FK_5dbpcm7h3g94nvpcbbyepxrut 
        foreign key (moneda_id) 
        references monedas (moneda_id)

    alter table Items 
        add constraint FK_r2vbewpq9569y5fv4goofxty0 
        foreign key (importe_importe_id) 
        references Importes (importe_id)

    alter table Items 
        add constraint FK_2sgd9wsd7p27pph257nrdcn09 
        foreign key (presupuesto_item_id) 
        references presupuestos (presupuesto_id)

    alter table categorias_reglas_negocio 
        add constraint FK_q4nkn0rutv8t1f6jfcadrru22 
        foreign key (reglasNegocio_regla_negocio_id) 
        references reglas_negocio (regla_negocio_id)

    alter table categorias_reglas_negocio 
        add constraint FK_csc70uomo830ayr524fy4gddf 
        foreign key (categorias_categoria_id) 
        references categorias (categoria_id)

    alter table egresos 
        add constraint FK_g02lybwwf91pt9tu1rcqdctj9 
        foreign key (docuComercial_doc_comercial_codigo) 
        references doc_comerciales (doc_comercial_codigo)

    alter table egresos 
        add constraint FK_fh43emxohmw8mi22ckwptmeqq 
        foreign key (medioPago_medio_pago_id) 
        references medios_de_pago (medio_pago_id)

    alter table egresos 
        add constraint FK_p06wtl7o7moc2kkajxvl3ba91 
        foreign key (proveedor_proveedor_id) 
        references proveedores (proveedor_id)

    alter table egresos_Items 
        add constraint FK_gk57jvogfns35pnvlxuf9eoto 
        foreign key (itemsEgreso_id) 
        references Items (id)

    alter table egresos_Items 
        add constraint FK_p0qb7fmjq4sk58p1b75dogiti 
        foreign key (egresos_egreso_id) 
        references egresos (egreso_id)

    alter table egresos_usuarios 
        add constraint FK_s1oppuctqllu0qxywb75rk2rb 
        foreign key (revisores_usuario_id) 
        references usuarios (usuario_id)

    alter table egresos_usuarios 
        add constraint FK_m1jpu1yb51q2xtu8ehux6b7u9 
        foreign key (egresos_egreso_id) 
        references egresos (egreso_id)

    alter table entidades_base 
        add constraint FK_9nu026uv6ubjldp9l925ybtmy 
        foreign key (organizacion) 
        references organizaciones (organizacion_id)

    alter table entidades_base 
        add constraint FK_ju8huqpitfb6g57sb1f38xs4w 
        foreign key (entidad_juridica) 
        references entidades_juridicas (organizacion)

    alter table entidades_juridicas 
        add constraint FK_h7n6qy1ovft394m8wo5lx7ljt 
        foreign key (entidad_juridica_direccion) 
        references direcciones (direccion_id)

    alter table entidades_juridicas 
        add constraint FK_owfxexiyla7vbuu7nxep1br44 
        foreign key (organizacion) 
        references organizaciones (organizacion_id)

    alter table mensajes 
        add constraint FK_9t1t3ntbmg6nehf47uis57kqv 
        foreign key (notificacion_id) 
        references notificaciones (notificacion_id)

    alter table notificaciones 
        add constraint FK_262wmo5rk1bnte2jcwwnl5ft7 
        foreign key (egreso_id) 
        references egresos (egreso_id)

    alter table notificaciones 
        add constraint FK_nf9nkoqrtf5vebkywp3adqfh0 
        foreign key (usuario_id) 
        references usuarios (usuario_id)

    alter table organizaciones 
        add constraint FK_ftuq5lki0welr0q5xmvrw4mgv 
        foreign key (categoria_id) 
        references categorias (categoria_id)

    alter table organizaciones_egresos 
        add constraint FK_a3k8ctqply3h0nhv69vt8sovw 
        foreign key (egresos_egreso_id) 
        references egresos (egreso_id)

    alter table organizaciones_egresos 
        add constraint FK_qm9q8x6b8yqxixxo2blkjy6h2 
        foreign key (organizaciones_organizacion_id) 
        references organizaciones (organizacion_id)

    alter table presupuestos 
        add constraint FK_mw0lvjakqp9erwqxe1o3br524 
        foreign key (doc_comercial_codigo) 
        references doc_comerciales (doc_comercial_codigo)

    alter table presupuestos 
        add constraint FK_d5xvilsnnrayvc1bo05hvytff 
        foreign key (proveedor_id) 
        references proveedores (proveedor_id)

    alter table presupuestos 
        add constraint FK_ocsr484bnefvpivf27rkq5frk 
        foreign key (egreso_id) 
        references egresos (egreso_id)

    alter table proveedores 
        add constraint FK_aeht8o58r6y0pjv065lh2su2j 
        foreign key (direccion_id) 
        references direcciones (direccion_id)
