--
-- PostgreSQL database dump
--

-- Dumped from database version 12.6 (Ubuntu 12.6-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.6 (Ubuntu 12.6-0ubuntu0.20.04.1)

-- Started on 2021-04-08 11:17:33 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- TOC entry 216 (class 1259 OID 1599506)
-- Name: act; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.act (
    id bigint NOT NULL,
    did bigint NOT NULL,
    number bigint NOT NULL,
    date date NOT NULL,
    type integer NOT NULL
);


ALTER TABLE public.act OWNER TO dent;

--
-- TOC entry 3109 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE act; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.act IS 'Акт';


--
-- TOC entry 3110 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN act.did; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act.did IS 'Договор';


--
-- TOC entry 3111 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN act.number; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act.number IS 'Номер';


--
-- TOC entry 3112 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN act.date; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act.date IS 'Дата';


--
-- TOC entry 3113 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN act.type; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act.type IS 'Тип акта';


--
-- TOC entry 215 (class 1259 OID 1599504)
-- Name: act_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.act_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.act_id_seq OWNER TO dent;

--
-- TOC entry 3114 (class 0 OID 0)
-- Dependencies: 215
-- Name: act_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.act_id_seq OWNED BY public.act.id;


--
-- TOC entry 217 (class 1259 OID 1599518)
-- Name: act_service; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.act_service (
    sid bigint NOT NULL,
    aid bigint NOT NULL,
    amount integer NOT NULL
);


ALTER TABLE public.act_service OWNER TO dent;

--
-- TOC entry 3115 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE act_service; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.act_service IS 'Связь Акт-Услуга';


--
-- TOC entry 3116 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN act_service.sid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act_service.sid IS 'Услуга';


--
-- TOC entry 3117 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN act_service.aid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act_service.aid IS 'Акт';


--
-- TOC entry 3118 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN act_service.amount; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.act_service.amount IS 'Количество';


--
-- TOC entry 212 (class 1259 OID 1599452)
-- Name: card; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.card (
    id bigint NOT NULL,
    number bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    diagnosis text,
    complaints text,
    anamnesis text,
    doc bigint NOT NULL,
    pid bigint NOT NULL
);


ALTER TABLE public.card OWNER TO dent;

--
-- TOC entry 3119 (class 0 OID 0)
-- Dependencies: 212
-- Name: TABLE card; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.card IS 'Медицинская карта';


--
-- TOC entry 3120 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.number; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.number IS 'Номер';


--
-- TOC entry 3121 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.date; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.date IS 'Дата';


--
-- TOC entry 3122 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.diagnosis; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.diagnosis IS 'Диагноз';


--
-- TOC entry 3123 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.complaints; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.complaints IS 'Жалобы';


--
-- TOC entry 3124 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.anamnesis; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.anamnesis IS 'Эпиданамнез';


--
-- TOC entry 3125 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.doc; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.doc IS 'Врач';


--
-- TOC entry 3126 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN card.pid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.card.pid IS 'Пациент';


--
-- TOC entry 211 (class 1259 OID 1599450)
-- Name: card_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.card_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.card_id_seq OWNER TO dent;

--
-- TOC entry 3127 (class 0 OID 0)
-- Dependencies: 211
-- Name: card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.card_id_seq OWNED BY public.card.id;


--
-- TOC entry 224 (class 1259 OID 1599613)
-- Name: client; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.client (
    id bigint NOT NULL,
    fio text NOT NULL,
    phone text NOT NULL,
    pid bigint
);


ALTER TABLE public.client OWNER TO dent;

--
-- TOC entry 3128 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE client; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.client IS 'Клиент';


--
-- TOC entry 3129 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN client.fio; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.client.fio IS 'ФИО';


--
-- TOC entry 3130 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN client.phone; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.client.phone IS 'Телефон';


--
-- TOC entry 3131 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN client.pid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.client.pid IS 'Пациент';


--
-- TOC entry 225 (class 1259 OID 1599625)
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_id_seq OWNER TO dent;

--
-- TOC entry 3132 (class 0 OID 0)
-- Dependencies: 225
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 203 (class 1259 OID 1599382)
-- Name: company; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.company (
    id bigint NOT NULL,
    name text NOT NULL,
    address text NOT NULL,
    director text NOT NULL
);


ALTER TABLE public.company OWNER TO dent;

--
-- TOC entry 3133 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE company; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.company IS 'Компания';


--
-- TOC entry 202 (class 1259 OID 1599380)
-- Name: company_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.company_id_seq OWNER TO dent;

--
-- TOC entry 3134 (class 0 OID 0)
-- Dependencies: 202
-- Name: company_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.company_id_seq OWNED BY public.company.id;


--
-- TOC entry 214 (class 1259 OID 1599474)
-- Name: contract; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.contract (
    id bigint NOT NULL,
    number bigint NOT NULL,
    date date NOT NULL,
    cid bigint NOT NULL,
    doc bigint NOT NULL,
    tech bigint,
    warranty text,
    pid bigint NOT NULL
);


ALTER TABLE public.contract OWNER TO dent;

--
-- TOC entry 3135 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE contract; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.contract IS 'Договор';


--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.number; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.number IS 'Номер';


--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.date; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.date IS 'Дата';


--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.cid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.cid IS 'Компания';


--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.doc; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.doc IS 'Врач';


--
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.tech; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.tech IS 'Зубной техник';


--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.warranty; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.warranty IS 'Гарантия';


--
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN contract.pid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.contract.pid IS 'Пациент';


--
-- TOC entry 213 (class 1259 OID 1599472)
-- Name: contract_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.contract_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contract_id_seq OWNER TO dent;

--
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 213
-- Name: contract_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.contract_id_seq OWNED BY public.contract.id;


--
-- TOC entry 205 (class 1259 OID 1599394)
-- Name: employee; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.employee (
    id bigint NOT NULL,
    fio text NOT NULL,
    post text NOT NULL,
    cid bigint NOT NULL,
    is_scheduled boolean DEFAULT false NOT NULL
);


ALTER TABLE public.employee OWNER TO dent;

--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE employee; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.employee IS 'Сотрудник';


--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN employee.post; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.employee.post IS 'Название типа сотрудника';


--
-- TOC entry 204 (class 1259 OID 1599392)
-- Name: employee_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employee_id_seq OWNER TO dent;

--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 204
-- Name: employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.employee_id_seq OWNED BY public.employee.id;


--
-- TOC entry 208 (class 1259 OID 1599423)
-- Name: find_out; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.find_out (
    id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.find_out OWNER TO dent;

--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE find_out; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.find_out IS 'Откуда узнал о компании';


--
-- TOC entry 207 (class 1259 OID 1599421)
-- Name: find_out_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.find_out_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.find_out_id_seq OWNER TO dent;

--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 207
-- Name: find_out_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.find_out_id_seq OWNED BY public.find_out.id;


--
-- TOC entry 223 (class 1259 OID 1599596)
-- Name: manipulation; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.manipulation (
    id bigint NOT NULL,
    name text NOT NULL,
    sid bigint NOT NULL
);


ALTER TABLE public.manipulation OWNER TO dent;

--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE manipulation; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.manipulation IS 'Манипуляции';


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN manipulation.name; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.manipulation.name IS 'Название';


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN manipulation.sid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.manipulation.sid IS 'Услуга';


--
-- TOC entry 222 (class 1259 OID 1599594)
-- Name: manipulation_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.manipulation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.manipulation_id_seq OWNER TO dent;

--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 222
-- Name: manipulation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.manipulation_id_seq OWNED BY public.manipulation.id;


--
-- TOC entry 206 (class 1259 OID 1599411)
-- Name: patient; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.patient (
    id bigint NOT NULL,
    fio text NOT NULL,
    phone text NOT NULL,
    address text,
    birth date,
    sex boolean,
    fid integer
);


ALTER TABLE public.patient OWNER TO dent;

--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE patient; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.patient IS 'Пациент';


--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.fio; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.fio IS 'ФИО';


--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.phone; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.phone IS 'Контактный телефон';


--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.address; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.address IS 'Адрес проживания';


--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.birth; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.birth IS 'Дата рождения';


--
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.sex; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.sex IS 'Пол';


--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN patient.fid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.patient.fid IS 'Откуда узнал о компании';


--
-- TOC entry 226 (class 1259 OID 1599631)
-- Name: patient_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.patient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.patient_id_seq OWNER TO dent;

--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 226
-- Name: patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.patient_id_seq OWNED BY public.patient.id;


--
-- TOC entry 221 (class 1259 OID 1599567)
-- Name: schedule; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.schedule (
    id bigint NOT NULL,
    start time without time zone NOT NULL,
    "end" time without time zone NOT NULL,
    dow integer NOT NULL
);


ALTER TABLE public.schedule OWNER TO dent;

--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE schedule; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.schedule IS 'График работы';


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN schedule.start; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.schedule.start IS 'Начало';


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN schedule."end"; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.schedule."end" IS 'Окончание';


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN schedule.dow; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.schedule.dow IS 'День недели';


--
-- TOC entry 220 (class 1259 OID 1599565)
-- Name: schedule_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.schedule_id_seq OWNER TO dent;

--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 220
-- Name: schedule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.schedule_id_seq OWNED BY public.schedule.id;


--
-- TOC entry 210 (class 1259 OID 1599440)
-- Name: service; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.service (
    id bigint NOT NULL,
    name text NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE public.service OWNER TO dent;

--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE service; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.service IS 'Услуга';


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN service.name; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.service.name IS 'Название';


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN service.price; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.service.price IS 'Цена';


--
-- TOC entry 209 (class 1259 OID 1599438)
-- Name: service_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.service_id_seq OWNER TO dent;

--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 209
-- Name: service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.service_id_seq OWNED BY public.service.id;


--
-- TOC entry 219 (class 1259 OID 1599535)
-- Name: slot; Type: TABLE; Schema: public; Owner: dent
--

CREATE TABLE public.slot (
    id bigint NOT NULL,
    enabled boolean NOT NULL,
    date date NOT NULL,
    cid bigint,
    doc bigint NOT NULL,
    "time" time without time zone NOT NULL,
    size time without time zone NOT NULL
);


ALTER TABLE public.slot OWNER TO dent;

--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE slot; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON TABLE public.slot IS 'Слот записи';


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN slot.enabled; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.slot.enabled IS 'Доступность слота';


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN slot.date; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.slot.date IS 'Дата';


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN slot.cid; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.slot.cid IS 'Клиент';


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN slot.doc; Type: COMMENT; Schema: public; Owner: dent
--

COMMENT ON COLUMN public.slot.doc IS 'Врач';


--
-- TOC entry 218 (class 1259 OID 1599533)
-- Name: slot_id_seq; Type: SEQUENCE; Schema: public; Owner: dent
--

CREATE SEQUENCE public.slot_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.slot_id_seq OWNER TO dent;

--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 218
-- Name: slot_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dent
--

ALTER SEQUENCE public.slot_id_seq OWNED BY public.slot.id;


--
-- TOC entry 2921 (class 2604 OID 1599509)
-- Name: act id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act ALTER COLUMN id SET DEFAULT nextval('public.act_id_seq'::regclass);


--
-- TOC entry 2919 (class 2604 OID 1599455)
-- Name: card id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.card ALTER COLUMN id SET DEFAULT nextval('public.card_id_seq'::regclass);


--
-- TOC entry 2925 (class 2604 OID 1599627)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 2913 (class 2604 OID 1599385)
-- Name: company id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.company ALTER COLUMN id SET DEFAULT nextval('public.company_id_seq'::regclass);


--
-- TOC entry 2920 (class 2604 OID 1599477)
-- Name: contract id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract ALTER COLUMN id SET DEFAULT nextval('public.contract_id_seq'::regclass);


--
-- TOC entry 2914 (class 2604 OID 1599397)
-- Name: employee id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.employee ALTER COLUMN id SET DEFAULT nextval('public.employee_id_seq'::regclass);


--
-- TOC entry 2917 (class 2604 OID 1599426)
-- Name: find_out id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.find_out ALTER COLUMN id SET DEFAULT nextval('public.find_out_id_seq'::regclass);


--
-- TOC entry 2924 (class 2604 OID 1599599)
-- Name: manipulation id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.manipulation ALTER COLUMN id SET DEFAULT nextval('public.manipulation_id_seq'::regclass);


--
-- TOC entry 2916 (class 2604 OID 1599633)
-- Name: patient id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.patient ALTER COLUMN id SET DEFAULT nextval('public.patient_id_seq'::regclass);


--
-- TOC entry 2923 (class 2604 OID 1599570)
-- Name: schedule id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.schedule ALTER COLUMN id SET DEFAULT nextval('public.schedule_id_seq'::regclass);


--
-- TOC entry 2918 (class 2604 OID 1599443)
-- Name: service id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.service ALTER COLUMN id SET DEFAULT nextval('public.service_id_seq'::regclass);


--
-- TOC entry 2922 (class 2604 OID 1599538)
-- Name: slot id; Type: DEFAULT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.slot ALTER COLUMN id SET DEFAULT nextval('public.slot_id_seq'::regclass);


--
-- TOC entry 2948 (class 2606 OID 1599517)
-- Name: act act_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act
    ADD CONSTRAINT act_pk PRIMARY KEY (id);


--
-- TOC entry 2950 (class 2606 OID 1599522)
-- Name: act_service act_service_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act_service
    ADD CONSTRAINT act_service_pk PRIMARY KEY (sid, aid);


--
-- TOC entry 2942 (class 2606 OID 1599471)
-- Name: card card_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_pk PRIMARY KEY (id);


--
-- TOC entry 2962 (class 2606 OID 1599630)
-- Name: client client_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);


--
-- TOC entry 2928 (class 2606 OID 1599391)
-- Name: company company_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pk PRIMARY KEY (id);


--
-- TOC entry 2945 (class 2606 OID 1599503)
-- Name: contract contract_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_pk PRIMARY KEY (id);


--
-- TOC entry 2931 (class 2606 OID 1599408)
-- Name: employee employee_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pk PRIMARY KEY (id);


--
-- TOC entry 2936 (class 2606 OID 1599432)
-- Name: find_out find_out_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.find_out
    ADD CONSTRAINT find_out_pk PRIMARY KEY (id);


--
-- TOC entry 2959 (class 2606 OID 1599610)
-- Name: manipulation manipulation_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.manipulation
    ADD CONSTRAINT manipulation_pk PRIMARY KEY (id);


--
-- TOC entry 2933 (class 2606 OID 1599420)
-- Name: patient patient_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pk PRIMARY KEY (id);


--
-- TOC entry 2956 (class 2606 OID 1599573)
-- Name: schedule schedule_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 1599449)
-- Name: service service_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pk PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 1599546)
-- Name: slot slot_pk; Type: CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.slot
    ADD CONSTRAINT slot_pk PRIMARY KEY (id);


--
-- TOC entry 2946 (class 1259 OID 1599515)
-- Name: act_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX act_id_uindex ON public.act USING btree (id);


--
-- TOC entry 2940 (class 1259 OID 1599469)
-- Name: card_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX card_id_uindex ON public.card USING btree (id);


--
-- TOC entry 2960 (class 1259 OID 1599628)
-- Name: client_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX client_id_uindex ON public.client USING btree (id);


--
-- TOC entry 2926 (class 1259 OID 1599389)
-- Name: company_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX company_id_uindex ON public.company USING btree (id);


--
-- TOC entry 2943 (class 1259 OID 1599501)
-- Name: contract_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX contract_id_uindex ON public.contract USING btree (id);


--
-- TOC entry 2929 (class 1259 OID 1599406)
-- Name: employee_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX employee_id_uindex ON public.employee USING btree (id);


--
-- TOC entry 2934 (class 1259 OID 1599430)
-- Name: find_out_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX find_out_id_uindex ON public.find_out USING btree (id);


--
-- TOC entry 2957 (class 1259 OID 1599608)
-- Name: manipulation_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX manipulation_id_uindex ON public.manipulation USING btree (id);


--
-- TOC entry 2954 (class 1259 OID 1599571)
-- Name: schedule_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX schedule_id_uindex ON public.schedule USING btree (id);


--
-- TOC entry 2937 (class 1259 OID 1599447)
-- Name: service_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX service_id_uindex ON public.service USING btree (id);


--
-- TOC entry 2951 (class 1259 OID 1599544)
-- Name: slot_id_uindex; Type: INDEX; Schema: public; Owner: dent
--

CREATE UNIQUE INDEX slot_id_uindex ON public.slot USING btree (id);


--
-- TOC entry 2971 (class 2606 OID 1599510)
-- Name: act act_contract_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act
    ADD CONSTRAINT act_contract_id_fk FOREIGN KEY (did) REFERENCES public.contract(id);


--
-- TOC entry 2973 (class 2606 OID 1599528)
-- Name: act_service act_service_act_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act_service
    ADD CONSTRAINT act_service_act_id_fk FOREIGN KEY (aid) REFERENCES public.act(id);


--
-- TOC entry 2972 (class 2606 OID 1599523)
-- Name: act_service act_service_service_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.act_service
    ADD CONSTRAINT act_service_service_id_fk FOREIGN KEY (sid) REFERENCES public.service(id);


--
-- TOC entry 2966 (class 2606 OID 1599464)
-- Name: card card_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_client_id_fk FOREIGN KEY (pid) REFERENCES public.patient(id);


--
-- TOC entry 2965 (class 2606 OID 1599459)
-- Name: card card_employee_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_employee_id_fk FOREIGN KEY (doc) REFERENCES public.employee(id);


--
-- TOC entry 2977 (class 2606 OID 1599620)
-- Name: client client_patient_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_patient_id_fk FOREIGN KEY (pid) REFERENCES public.patient(id);


--
-- TOC entry 2970 (class 2606 OID 1599496)
-- Name: contract contract_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_client_id_fk FOREIGN KEY (pid) REFERENCES public.patient(id);


--
-- TOC entry 2967 (class 2606 OID 1599481)
-- Name: contract contract_company_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_company_id_fk FOREIGN KEY (cid) REFERENCES public.company(id);


--
-- TOC entry 2968 (class 2606 OID 1599486)
-- Name: contract contract_employee_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_employee_id_fk FOREIGN KEY (doc) REFERENCES public.employee(id);


--
-- TOC entry 2969 (class 2606 OID 1599491)
-- Name: contract contract_employee_id_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_employee_id_fk_2 FOREIGN KEY (tech) REFERENCES public.employee(id);


--
-- TOC entry 2963 (class 2606 OID 1599401)
-- Name: employee employee_company_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_company_id_fk FOREIGN KEY (cid) REFERENCES public.company(id);


--
-- TOC entry 2976 (class 2606 OID 1599603)
-- Name: manipulation manipulation_service_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.manipulation
    ADD CONSTRAINT manipulation_service_id_fk FOREIGN KEY (sid) REFERENCES public.service(id);


--
-- TOC entry 2964 (class 2606 OID 1599433)
-- Name: patient patient_find_out_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_find_out_id_fk FOREIGN KEY (fid) REFERENCES public.find_out(id);


--
-- TOC entry 2975 (class 2606 OID 1599634)
-- Name: slot slot_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.slot
    ADD CONSTRAINT slot_client_id_fk FOREIGN KEY (cid) REFERENCES public.client(id);


--
-- TOC entry 2974 (class 2606 OID 1599589)
-- Name: slot slot_employee_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: dent
--

ALTER TABLE ONLY public.slot
    ADD CONSTRAINT slot_employee_id_fk FOREIGN KEY (doc) REFERENCES public.employee(id);


-- Completed on 2021-04-08 11:17:33 MSK

--
-- PostgreSQL database dump complete
--

