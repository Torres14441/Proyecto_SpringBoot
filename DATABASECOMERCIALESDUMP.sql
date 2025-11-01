--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-10-20 10:37:26

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16487)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16486)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO postgres;

--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 217
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 222 (class 1259 OID 16505)
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    tel character varying(20),
    ccr character varying(4)
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16504)
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO postgres;

--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 221
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;


--
-- TOC entry 240 (class 1259 OID 16640)
-- Name: details_clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.details_clients (
    id bigint NOT NULL,
    id_cliente bigint NOT NULL,
    email character varying(100),
    address character varying(255),
    created timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP,
    updated timestamp(6) without time zone
);


ALTER TABLE public.details_clients OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 16639)
-- Name: details_clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.details_clients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.details_clients_id_seq OWNER TO postgres;

--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 239
-- Name: details_clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.details_clients_id_seq OWNED BY public.details_clients.id;


--
-- TOC entry 238 (class 1259 OID 16623)
-- Name: details_orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.details_orders (
    id bigint NOT NULL,
    id_order bigint NOT NULL,
    id_product bigint NOT NULL,
    quantity integer NOT NULL,
    unit_price numeric(10,2) NOT NULL,
    created timestamp(6) without time zone,
    updated timestamp(6) without time zone
);


ALTER TABLE public.details_orders OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16622)
-- Name: details_orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.details_orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.details_orders_id_seq OWNER TO postgres;

--
-- TOC entry 5054 (class 0 OID 0)
-- Dependencies: 237
-- Name: details_orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.details_orders_id_seq OWNED BY public.details_orders.id;


--
-- TOC entry 234 (class 1259 OID 16584)
-- Name: inventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inventory (
    id bigint NOT NULL,
    id_product bigint NOT NULL,
    id_localization bigint NOT NULL,
    stock_actual integer DEFAULT 0,
    stock_min integer DEFAULT 0,
    update timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.inventory OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16583)
-- Name: inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.inventory_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.inventory_id_seq OWNER TO postgres;

--
-- TOC entry 5055 (class 0 OID 0)
-- Dependencies: 233
-- Name: inventory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.inventory_id_seq OWNED BY public.inventory.id;


--
-- TOC entry 232 (class 1259 OID 16575)
-- Name: localizations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.localizations (
    id bigint NOT NULL,
    intern_code character varying(6) NOT NULL
);


ALTER TABLE public.localizations OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16574)
-- Name: localizaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.localizaciones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.localizaciones_id_seq OWNER TO postgres;

--
-- TOC entry 5056 (class 0 OID 0)
-- Dependencies: 231
-- Name: localizaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.localizaciones_id_seq OWNED BY public.localizations.id;


--
-- TOC entry 236 (class 1259 OID 16604)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    id_client bigint,
    id_user bigint NOT NULL,
    total numeric(10,2) NOT NULL,
    created timestamp without time zone,
    updated timestamp without time zone,
    status character varying(255) DEFAULT 'Activa'::character varying
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16603)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO postgres;

--
-- TOC entry 5057 (class 0 OID 0)
-- Dependencies: 235
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 242 (class 1259 OID 16753)
-- Name: products_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products_categories (
    id integer NOT NULL,
    product_id bigint NOT NULL,
    category_id bigint NOT NULL
);


ALTER TABLE public.products_categories OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 16752)
-- Name: product_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_category_id_seq OWNER TO postgres;

--
-- TOC entry 5058 (class 0 OID 0)
-- Dependencies: 241
-- Name: product_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_category_id_seq OWNED BY public.products_categories.id;


--
-- TOC entry 230 (class 1259 OID 16556)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(1000),
    price double precision NOT NULL,
    id_type_inv bigint NOT NULL,
    cpr character varying(10)
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16555)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 5059 (class 0 OID 0)
-- Dependencies: 229
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 226 (class 1259 OID 16527)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16526)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 5060 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 220 (class 1259 OID 16496)
-- Name: type_inventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.type_inventory (
    id bigint NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.type_inventory OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16495)
-- Name: type_inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.type_inventory_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.type_inventory_id_seq OWNER TO postgres;

--
-- TOC entry 5061 (class 0 OID 0)
-- Dependencies: 219
-- Name: type_inventory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.type_inventory_id_seq OWNED BY public.type_inventory.id;


--
-- TOC entry 224 (class 1259 OID 16512)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    username character varying(20) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    enabled integer DEFAULT 1 NOT NULL,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated timestamp without time zone,
    status integer
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16511)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 5062 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 228 (class 1259 OID 16536)
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    assigned_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16535)
-- Name: users_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_roles_id_seq OWNER TO postgres;

--
-- TOC entry 5063 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_roles_id_seq OWNED BY public.users_roles.id;


--
-- TOC entry 4802 (class 2604 OID 16771)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 4804 (class 2604 OID 16975)
-- Name: clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- TOC entry 4820 (class 2604 OID 16994)
-- Name: details_clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_clients ALTER COLUMN id SET DEFAULT nextval('public.details_clients_id_seq'::regclass);


--
-- TOC entry 4819 (class 2604 OID 17034)
-- Name: details_orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_orders ALTER COLUMN id SET DEFAULT nextval('public.details_orders_id_seq'::regclass);


--
-- TOC entry 4813 (class 2604 OID 16791)
-- Name: inventory id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory ALTER COLUMN id SET DEFAULT nextval('public.inventory_id_seq'::regclass);


--
-- TOC entry 4812 (class 2604 OID 16927)
-- Name: localizations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.localizations ALTER COLUMN id SET DEFAULT nextval('public.localizaciones_id_seq'::regclass);


--
-- TOC entry 4817 (class 2604 OID 17059)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 4811 (class 2604 OID 16813)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 4822 (class 2604 OID 16756)
-- Name: products_categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories ALTER COLUMN id SET DEFAULT nextval('public.product_category_id_seq'::regclass);


--
-- TOC entry 4808 (class 2604 OID 16655)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4803 (class 2604 OID 16879)
-- Name: type_inventory id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_inventory ALTER COLUMN id SET DEFAULT nextval('public.type_inventory_id_seq'::regclass);


--
-- TOC entry 4805 (class 2604 OID 16668)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4809 (class 2604 OID 16539)
-- Name: users_roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles ALTER COLUMN id SET DEFAULT nextval('public.users_roles_id_seq'::regclass);


--
-- TOC entry 5021 (class 0 OID 16487)
-- Dependencies: 218
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, name) FROM stdin;
1	Electrónica
2	Smartphones
3	Ofertas
4	Portatiles
5	Televisores
6	Electrodomésticos
7	Desinfectante
8	hogar
9	Teclados
10	Computadoras
11	Accesorios
\.


--
-- TOC entry 5025 (class 0 OID 16505)
-- Dependencies: 222
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (id, name, tel, ccr) FROM stdin;
1	José Martínez	7777-8888	CL01
2	René Zelaya	4444-7777	CL02
7	Samuel Ramiréz	4444-6666	CL03
8	Pablo Quijada	3333-9999	CL04
9	Erick Romero	3232-8989	CL05
\.


--
-- TOC entry 5043 (class 0 OID 16640)
-- Dependencies: 240
-- Data for Name: details_clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.details_clients (id, id_cliente, email, address, created, updated) FROM stdin;
7	8	Osorto@example.com	Santa tecla, El Salvador	2025-10-18 12:41:02.817	2025-10-18 13:49:06.584
8	9	Romero@example.com	Mejicanos, El Salvador	2025-10-18 14:06:12.886	2025-10-18 14:06:12.886
1	1	jose@example.com	Soyapango, El Salvador	2025-10-20 11:40:15.774	2025-10-20 11:40:15.774
2	2	rene@example.com	San Salvador, El Salvador	2025-10-17 13:48:06.584	2025-10-17 13:48:06.584
6	7	Jsam@example.com	San Salvador, El Salvador	2025-10-17 12:40:15.774	2025-10-18 12:40:15.774
\.


--
-- TOC entry 5041 (class 0 OID 16623)
-- Dependencies: 238
-- Data for Name: details_orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.details_orders (id, id_order, id_product, quantity, unit_price, created, updated) FROM stdin;
1	1	5	2	1200.00	2025-10-18 15:57:00.151	2025-10-18 15:57:00.151
2	1	6	1	1200.00	2025-10-18 15:57:00.166	2025-10-18 15:57:00.166
3	2	7	2	1000.00	2025-10-18 16:04:33.875	2025-10-18 16:04:33.875
4	2	9	1	1500.00	2025-10-18 16:04:33.889	2025-10-18 16:04:33.889
5	3	10	1	1200.00	2025-10-18 16:23:15.974	2025-10-18 16:23:15.974
6	5	10	3	1200.00	\N	\N
7	4	5	1	900.00	\N	\N
8	6	5	1	900.00	\N	\N
10	7	5	4	900.00	\N	\N
11	8	5	3	900.00	\N	\N
\.


--
-- TOC entry 5037 (class 0 OID 16584)
-- Dependencies: 234
-- Data for Name: inventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inventory (id, id_product, id_localization, stock_actual, stock_min, update) FROM stdin;
1	9	2	3	1	2025-10-18 09:38:45.811827
2	5	2	7	1	2025-10-19 18:30:15.083473
3	7	1	50	1	2025-10-19 18:39:28.663573
4	12	2	50	1	2025-10-20 08:18:21.94232
\.


--
-- TOC entry 5035 (class 0 OID 16575)
-- Dependencies: 232
-- Data for Name: localizations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.localizations (id, intern_code) FROM stdin;
1	A01
2	A02
\.


--
-- TOC entry 5039 (class 0 OID 16604)
-- Dependencies: 236
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, id_client, id_user, total, created, updated, status) FROM stdin;
1	1	1	3600.00	2025-10-18 15:57:00.118	2025-10-18 15:57:00.118	Activa
2	2	1	3500.00	2025-10-18 16:04:33.843	2025-10-18 16:04:33.843	Activa
3	7	1	1200.00	2025-10-18 16:23:15.947	2025-10-18 16:23:15.947	Activa
5	8	1	3600.00	2025-10-18 16:30:35.074	2025-10-18 16:30:35.074	Activa
4	8	1	900.00	2025-10-18 16:25:26.262	2025-10-18 17:28:03.858	Activa
6	8	1	900.00	2025-10-19 16:13:05.782	2025-10-19 16:13:05.782	Activa
7	9	1	3600.00	2025-10-19 16:18:43.102	2025-10-19 16:35:58.126	Cancelada
8	9	1	2700.00	2025-10-19 18:30:15.044	2025-10-19 18:30:15.044	Activa
\.


--
-- TOC entry 5033 (class 0 OID 16556)
-- Dependencies: 230
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, description, price, id_type_inv, cpr) FROM stdin;
5	Celular Xiaomi	Celular de gama alta	900	2	cpr01
6	Laptop LG	Laptop de gama media	1200	2	cpr02
7	Televisor LG	Tlevisor 4k de 24 pulgadas	1000	3	cpr03
9	Televisor LG	Tlevisor 4k de 50 pulgadas	1500	3	cpr04
10	producto 5	producto generico	1200	5	cpr05
11	Teclado mécanico	Teclado de switch azul	71.99	2	cpr06
12	Kit de limpieza para computadoras	Kit de limpieza de multiples herramientas	25	2	cpr07
\.


--
-- TOC entry 5045 (class 0 OID 16753)
-- Dependencies: 242
-- Data for Name: products_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products_categories (id, product_id, category_id) FROM stdin;
9	5	1
10	5	2
11	6	1
12	6	4
13	7	1
14	7	5
23	9	5
25	10	7
26	10	8
30	11	9
31	11	10
32	11	11
33	12	9
34	12	10
35	12	11
\.


--
-- TOC entry 5029 (class 0 OID 16527)
-- Dependencies: 226
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_ADMIN
2	ROLE_USER
\.


--
-- TOC entry 5023 (class 0 OID 16496)
-- Dependencies: 220
-- Data for Name: type_inventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.type_inventory (id, name) FROM stdin;
1	Digital
2	Tecnología
3	Entretenimiento
4	Ofertas
5	Limpieza
\.


--
-- TOC entry 5027 (class 0 OID 16512)
-- Dependencies: 224
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, username, email, password, enabled, created, updated, status) FROM stdin;
1	José Torres	Jtorres	Jtorres@example.com	$2a$10$v7jSk7f6gf8Jr0/zZa1gdOOxQK5Dt2iD1V4yz7gGJevVYa.slUBVW	1	2025-10-12 14:21:46.632	2025-10-12 14:21:46.632	1
2	Isrrael Alvarenga	Itorres935	ITorres@Example.com	$2a$10$iBiDnaDiodRt3HXi9Aed/u4wIFKA1TMFIHHNmB9wjTPG9KLFMPNES	1	2025-10-14 10:35:10.26	2025-10-14 10:35:10.26	1
3	Mauricio Reyes	Polanco2020	Reyes@Example.com	$2a$10$.SGFWE4MmP.7ShVrc38ZD.eYeRMZ90.ctsqcAww0uriM8beShMgBS	1	2025-10-18 07:57:56.228	2025-10-18 12:35:11.885	0
4	Jesús Alvarado	Jalvarado30	Alvarado@Example.com	$2a$10$IXH.ea939e1Ya8jgBuilvu0BRPDx0cS7Q9WSjUKvnsWGubrrZsViK	1	2025-10-19 13:04:29.391	2025-10-19 13:04:29.399	1
7	Juan Perez	JPerez30	Jperez@Example.com	$2a$10$AgSyNVxe2fEI6lk8d.01i.g3X5xl3TFDwHeaDvxz2.dPi3RiAIiSy	1	2025-10-19 13:12:21.061	2025-10-19 13:13:37.022	0
\.


--
-- TOC entry 5031 (class 0 OID 16536)
-- Dependencies: 228
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_roles (id, user_id, role_id, assigned_at) FROM stdin;
1	1	2	2025-10-12 14:21:46.521933
2	1	1	2025-10-12 14:21:46.521933
7	2	2	2025-10-17 21:16:49.363315
18	3	2	2025-10-18 12:35:11.884395
19	4	2	2025-10-19 13:04:29.31587
21	7	2	2025-10-19 13:13:06.017431
22	7	1	2025-10-19 13:13:06.017431
\.


--
-- TOC entry 5064 (class 0 OID 0)
-- Dependencies: 217
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 11, true);


--
-- TOC entry 5065 (class 0 OID 0)
-- Dependencies: 221
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clients_id_seq', 10, true);


--
-- TOC entry 5066 (class 0 OID 0)
-- Dependencies: 239
-- Name: details_clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.details_clients_id_seq', 9, true);


--
-- TOC entry 5067 (class 0 OID 0)
-- Dependencies: 237
-- Name: details_orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.details_orders_id_seq', 11, true);


--
-- TOC entry 5068 (class 0 OID 0)
-- Dependencies: 233
-- Name: inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.inventory_id_seq', 4, true);


--
-- TOC entry 5069 (class 0 OID 0)
-- Dependencies: 231
-- Name: localizaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.localizaciones_id_seq', 2, true);


--
-- TOC entry 5070 (class 0 OID 0)
-- Dependencies: 235
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 8, true);


--
-- TOC entry 5071 (class 0 OID 0)
-- Dependencies: 241
-- Name: product_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_category_id_seq', 35, true);


--
-- TOC entry 5072 (class 0 OID 0)
-- Dependencies: 229
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 12, true);


--
-- TOC entry 5073 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- TOC entry 5074 (class 0 OID 0)
-- Dependencies: 219
-- Name: type_inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.type_inventory_id_seq', 5, true);


--
-- TOC entry 5075 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- TOC entry 5076 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_roles_id_seq', 22, true);


--
-- TOC entry 4824 (class 2606 OID 16773)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4828 (class 2606 OID 16977)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 4856 (class 2606 OID 16996)
-- Name: details_clients details_clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_clients
    ADD CONSTRAINT details_clients_pkey PRIMARY KEY (id);


--
-- TOC entry 4854 (class 2606 OID 17036)
-- Name: details_orders details_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_orders
    ADD CONSTRAINT details_orders_pkey PRIMARY KEY (id);


--
-- TOC entry 4850 (class 2606 OID 16793)
-- Name: inventory inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_pkey PRIMARY KEY (id);


--
-- TOC entry 4848 (class 2606 OID 16929)
-- Name: localizations localizaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.localizations
    ADD CONSTRAINT localizaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 4852 (class 2606 OID 17061)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 4858 (class 2606 OID 16758)
-- Name: products_categories product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- TOC entry 4860 (class 2606 OID 16868)
-- Name: products_categories product_category_product_id_category_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories
    ADD CONSTRAINT product_category_product_id_category_id_key UNIQUE (product_id, category_id);


--
-- TOC entry 4846 (class 2606 OID 16815)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 4836 (class 2606 OID 16534)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 4838 (class 2606 OID 16657)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4826 (class 2606 OID 16881)
-- Name: type_inventory type_inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_inventory
    ADD CONSTRAINT type_inventory_pkey PRIMARY KEY (id);


--
-- TOC entry 4840 (class 2606 OID 16714)
-- Name: users_roles ukq3r1u8cne2rw2hkr899xuh7vj; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT ukq3r1u8cne2rw2hkr899xuh7vj UNIQUE (user_id, role_id);


--
-- TOC entry 4862 (class 2606 OID 16898)
-- Name: products_categories ukq76m5236wcbvul3k16wngspe7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories
    ADD CONSTRAINT ukq76m5236wcbvul3k16wngspe7 UNIQUE (product_id, category_id);


--
-- TOC entry 4830 (class 2606 OID 16523)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4832 (class 2606 OID 16670)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4842 (class 2606 OID 16542)
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4844 (class 2606 OID 16702)
-- Name: users_roles users_roles_user_id_role_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_user_id_role_id_key UNIQUE (user_id, role_id);


--
-- TOC entry 4834 (class 2606 OID 16733)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4872 (class 2606 OID 17009)
-- Name: details_clients details_clients_id_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_clients
    ADD CONSTRAINT details_clients_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES public.clients(id);


--
-- TOC entry 4870 (class 2606 OID 17062)
-- Name: details_orders details_orders_id_orden_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_orders
    ADD CONSTRAINT details_orders_id_orden_fkey FOREIGN KEY (id_order) REFERENCES public.orders(id);


--
-- TOC entry 4871 (class 2606 OID 17050)
-- Name: details_orders details_orders_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.details_orders
    ADD CONSTRAINT details_orders_id_producto_fkey FOREIGN KEY (id_product) REFERENCES public.products(id);


--
-- TOC entry 4866 (class 2606 OID 16930)
-- Name: inventory inventory_id_localizacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_id_localizacion_fkey FOREIGN KEY (id_localization) REFERENCES public.localizations(id);


--
-- TOC entry 4867 (class 2606 OID 16826)
-- Name: inventory inventory_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_id_producto_fkey FOREIGN KEY (id_product) REFERENCES public.products(id);


--
-- TOC entry 4868 (class 2606 OID 17071)
-- Name: orders orders_id_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_id_cliente_fkey FOREIGN KEY (id_client) REFERENCES public.clients(id);


--
-- TOC entry 4869 (class 2606 OID 17080)
-- Name: orders orders_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_id_usuario_fkey FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- TOC entry 4873 (class 2606 OID 16869)
-- Name: products_categories product_category_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories
    ADD CONSTRAINT product_category_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE CASCADE;


--
-- TOC entry 4874 (class 2606 OID 16857)
-- Name: products_categories product_category_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products_categories
    ADD CONSTRAINT product_category_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- TOC entry 4865 (class 2606 OID 16905)
-- Name: products products_id_tipo_inv_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_id_tipo_inv_fkey FOREIGN KEY (id_type_inv) REFERENCES public.type_inventory(id);


--
-- TOC entry 4863 (class 2606 OID 16703)
-- Name: users_roles users_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4864 (class 2606 OID 16691)
-- Name: users_roles users_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2025-10-20 10:37:27

--
-- PostgreSQL database dump complete
--

