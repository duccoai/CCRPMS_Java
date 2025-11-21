--
-- PostgreSQL database dump
--

\restrict eZJviabC0yeHvY4nLcNXN0Iky082sTnlMygPDe7q90Y84Wjc74oOJZfmmvdtPQc

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-11-20 22:31:18

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
-- TOC entry 245 (class 1259 OID 74029)
-- Name: answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.answers (
    id bigint NOT NULL,
    text text NOT NULL,
    question_id bigint,
    submission_id bigint
);


ALTER TABLE public.answers OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 74028)
-- Name: answers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.answers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.answers_id_seq OWNER TO postgres;

--
-- TOC entry 5216 (class 0 OID 0)
-- Dependencies: 244
-- Name: answers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.answers_id_seq OWNED BY public.answers.id;


--
-- TOC entry 227 (class 1259 OID 32952)
-- Name: applications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.applications (
    id bigint NOT NULL,
    candidate_name character varying(200),
    candidate_email character varying(150),
    campaign_id bigint,
    status character varying(50) DEFAULT 'PENDING'::character varying,
    submitted_at timestamp without time zone DEFAULT now(),
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    user_id bigint NOT NULL,
    job_id bigint,
    cv_url character varying(255),
    score_exam double precision,
    type character varying(255),
    candidate_id bigint,
    CONSTRAINT applications_type_check CHECK (((type)::text = ANY ((ARRAY['REGULAR'::character varying, 'PROMOTION'::character varying])::text[])))
);


ALTER TABLE public.applications OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 32951)
-- Name: applications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.applications_id_seq OWNER TO postgres;

--
-- TOC entry 5217 (class 0 OID 0)
-- Dependencies: 226
-- Name: applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.applications_id_seq OWNED BY public.applications.id;


--
-- TOC entry 229 (class 1259 OID 32967)
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_logs (
    id bigint NOT NULL,
    actor character varying(150),
    action character varying(255),
    details text,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.audit_logs OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 32966)
-- Name: audit_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.audit_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.audit_logs_id_seq OWNER TO postgres;

--
-- TOC entry 5218 (class 0 OID 0)
-- Dependencies: 228
-- Name: audit_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.audit_logs_id_seq OWNED BY public.audit_logs.id;


--
-- TOC entry 225 (class 1259 OID 32938)
-- Name: campaigns; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.campaigns (
    id bigint NOT NULL,
    code character varying(100) NOT NULL,
    title character varying(255) NOT NULL,
    description text,
    start_date date,
    end_date date,
    status character varying(50)
);


ALTER TABLE public.campaigns OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 32937)
-- Name: campaigns_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.campaigns_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.campaigns_id_seq OWNER TO postgres;

--
-- TOC entry 5219 (class 0 OID 0)
-- Dependencies: 224
-- Name: campaigns_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.campaigns_id_seq OWNED BY public.campaigns.id;


--
-- TOC entry 239 (class 1259 OID 73976)
-- Name: exams; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exams (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description text,
    job_id bigint,
    duration integer,
    active boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.exams OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 73975)
-- Name: exams_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exams_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exams_id_seq OWNER TO postgres;

--
-- TOC entry 5220 (class 0 OID 0)
-- Dependencies: 238
-- Name: exams_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exams_id_seq OWNED BY public.exams.id;


--
-- TOC entry 219 (class 1259 OID 32887)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 73866)
-- Name: interview; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interview (
    id bigint NOT NULL,
    comment character varying(255),
    interview_date timestamp(6) without time zone,
    location character varying(255),
    score double precision,
    score_exam double precision,
    status character varying(255),
    application_id bigint,
    note character varying(255),
    CONSTRAINT interview_status_check CHECK (((status)::text = ANY ((ARRAY['SCHEDULED'::character varying, 'COMPLETED'::character varying, 'PENDING'::character varying, 'INTERVIEWING'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying, 'HIRED'::character varying])::text[])))
);


ALTER TABLE public.interview OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 73865)
-- Name: interview_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interview_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interview_id_seq OWNER TO postgres;

--
-- TOC entry 5221 (class 0 OID 0)
-- Dependencies: 236
-- Name: interview_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interview_id_seq OWNED BY public.interview.id;


--
-- TOC entry 235 (class 1259 OID 65657)
-- Name: interviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interviews (
    id bigint NOT NULL,
    comment character varying(255),
    interview_date timestamp(6) without time zone,
    location character varying(255),
    note character varying(255),
    score double precision,
    status character varying(255),
    application_id bigint NOT NULL,
    score_exam double precision,
    CONSTRAINT interviews_status_check CHECK (((status)::text = ANY ((ARRAY['SCHEDULED'::character varying, 'COMPLETED'::character varying, 'CANCELED'::character varying])::text[])))
);


ALTER TABLE public.interviews OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 65656)
-- Name: interviews_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interviews_id_seq OWNER TO postgres;

--
-- TOC entry 5222 (class 0 OID 0)
-- Dependencies: 234
-- Name: interviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interviews_id_seq OWNED BY public.interviews.id;


--
-- TOC entry 231 (class 1259 OID 32998)
-- Name: jobs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jobs (
    id bigint NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    description text,
    location character varying(255),
    salary_range character varying(255),
    status character varying(255),
    title character varying(255) NOT NULL,
    recruiter_id bigint,
    CONSTRAINT jobs_status_check CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'CLOSED'::character varying])::text[])))
);


ALTER TABLE public.jobs OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 32997)
-- Name: jobs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jobs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jobs_id_seq OWNER TO postgres;

--
-- TOC entry 5223 (class 0 OID 0)
-- Dependencies: 230
-- Name: jobs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jobs_id_seq OWNED BY public.jobs.id;


--
-- TOC entry 251 (class 1259 OID 82080)
-- Name: notifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifications (
    id bigint NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    message character varying(255),
    read_status boolean NOT NULL,
    title character varying(255),
    user_id bigint
);


ALTER TABLE public.notifications OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 82079)
-- Name: notifications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notifications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notifications_id_seq OWNER TO postgres;

--
-- TOC entry 5224 (class 0 OID 0)
-- Dependencies: 250
-- Name: notifications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notifications_id_seq OWNED BY public.notifications.id;


--
-- TOC entry 249 (class 1259 OID 74121)
-- Name: promotion_applications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.promotion_applications (
    id bigint NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    certificates character varying(500),
    flight_hours integer NOT NULL,
    performance_result character varying(500),
    score_exam double precision,
    status character varying(255),
    team_lead_suggestion character varying(500),
    candidate_id bigint NOT NULL,
    CONSTRAINT promotion_applications_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'APPROVED'::character varying, 'HIRED'::character varying, 'REJECTED'::character varying])::text[])))
);


ALTER TABLE public.promotion_applications OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 74120)
-- Name: promotion_applications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.promotion_applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.promotion_applications_id_seq OWNER TO postgres;

--
-- TOC entry 5225 (class 0 OID 0)
-- Dependencies: 248
-- Name: promotion_applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.promotion_applications_id_seq OWNED BY public.promotion_applications.id;


--
-- TOC entry 247 (class 1259 OID 74103)
-- Name: promotion_requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.promotion_requests (
    id bigint NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    certificates character varying(255),
    flight_hours integer NOT NULL,
    performance_result character varying(255),
    status character varying(255),
    team_lead_suggestion character varying(255),
    user_id bigint NOT NULL,
    CONSTRAINT promotion_requests_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying])::text[])))
);


ALTER TABLE public.promotion_requests OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 74102)
-- Name: promotion_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.promotion_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.promotion_requests_id_seq OWNER TO postgres;

--
-- TOC entry 5226 (class 0 OID 0)
-- Dependencies: 246
-- Name: promotion_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.promotion_requests_id_seq OWNED BY public.promotion_requests.id;


--
-- TOC entry 241 (class 1259 OID 73990)
-- Name: questions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.questions (
    id bigint NOT NULL,
    exam_id bigint,
    content text NOT NULL,
    option_a text NOT NULL,
    option_b text NOT NULL,
    option_c text NOT NULL,
    option_d text NOT NULL,
    correct_answer character varying(2) NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    optiona character varying(255),
    optionb character varying(255),
    optionc character varying(255),
    optiond character varying(255)
);


ALTER TABLE public.questions OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 73989)
-- Name: questions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.questions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.questions_id_seq OWNER TO postgres;

--
-- TOC entry 5227 (class 0 OID 0)
-- Dependencies: 240
-- Name: questions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.questions_id_seq OWNED BY public.questions.id;


--
-- TOC entry 221 (class 1259 OID 32905)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 32904)
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
-- TOC entry 5228 (class 0 OID 0)
-- Dependencies: 220
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 243 (class 1259 OID 74012)
-- Name: submissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.submissions (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    exam_id bigint,
    application_id bigint,
    score double precision DEFAULT 0,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    submitted_at timestamp(6) without time zone,
    answers_json text
);


ALTER TABLE public.submissions OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 74011)
-- Name: submissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.submissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.submissions_id_seq OWNER TO postgres;

--
-- TOC entry 5229 (class 0 OID 0)
-- Dependencies: 242
-- Name: submissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.submissions_id_seq OWNED BY public.submissions.id;


--
-- TOC entry 233 (class 1259 OID 33047)
-- Name: tests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tests (
    id bigint NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    description character varying(255),
    title character varying(255),
    job_id bigint
);


ALTER TABLE public.tests OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 33046)
-- Name: tests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tests_id_seq OWNER TO postgres;

--
-- TOC entry 5230 (class 0 OID 0)
-- Dependencies: 232
-- Name: tests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tests_id_seq OWNED BY public.tests.id;


--
-- TOC entry 223 (class 1259 OID 32916)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(150),
    full_name character varying(200),
    role_id integer,
    active boolean DEFAULT true,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    avatar_url character varying(255),
    bio character varying(2000),
    cv_url character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 32915)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 5231 (class 0 OID 0)
-- Dependencies: 222
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4960 (class 2604 OID 74032)
-- Name: answers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers ALTER COLUMN id SET DEFAULT nextval('public.answers_id_seq'::regclass);


--
-- TOC entry 4942 (class 2604 OID 32955)
-- Name: applications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications ALTER COLUMN id SET DEFAULT nextval('public.applications_id_seq'::regclass);


--
-- TOC entry 4945 (class 2604 OID 32970)
-- Name: audit_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs ALTER COLUMN id SET DEFAULT nextval('public.audit_logs_id_seq'::regclass);


--
-- TOC entry 4941 (class 2604 OID 32941)
-- Name: campaigns id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campaigns ALTER COLUMN id SET DEFAULT nextval('public.campaigns_id_seq'::regclass);


--
-- TOC entry 4951 (class 2604 OID 73979)
-- Name: exams id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exams ALTER COLUMN id SET DEFAULT nextval('public.exams_id_seq'::regclass);


--
-- TOC entry 4950 (class 2604 OID 73869)
-- Name: interview id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interview ALTER COLUMN id SET DEFAULT nextval('public.interview_id_seq'::regclass);


--
-- TOC entry 4949 (class 2604 OID 65660)
-- Name: interviews id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interviews ALTER COLUMN id SET DEFAULT nextval('public.interviews_id_seq'::regclass);


--
-- TOC entry 4947 (class 2604 OID 33001)
-- Name: jobs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs ALTER COLUMN id SET DEFAULT nextval('public.jobs_id_seq'::regclass);


--
-- TOC entry 4963 (class 2604 OID 82083)
-- Name: notifications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications ALTER COLUMN id SET DEFAULT nextval('public.notifications_id_seq'::regclass);


--
-- TOC entry 4962 (class 2604 OID 74124)
-- Name: promotion_applications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_applications ALTER COLUMN id SET DEFAULT nextval('public.promotion_applications_id_seq'::regclass);


--
-- TOC entry 4961 (class 2604 OID 74106)
-- Name: promotion_requests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_requests ALTER COLUMN id SET DEFAULT nextval('public.promotion_requests_id_seq'::regclass);


--
-- TOC entry 4955 (class 2604 OID 73993)
-- Name: questions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions ALTER COLUMN id SET DEFAULT nextval('public.questions_id_seq'::regclass);


--
-- TOC entry 4936 (class 2604 OID 32908)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4956 (class 2604 OID 74015)
-- Name: submissions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submissions ALTER COLUMN id SET DEFAULT nextval('public.submissions_id_seq'::regclass);


--
-- TOC entry 4948 (class 2604 OID 33050)
-- Name: tests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tests ALTER COLUMN id SET DEFAULT nextval('public.tests_id_seq'::regclass);


--
-- TOC entry 4937 (class 2604 OID 32919)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 5204 (class 0 OID 74029)
-- Dependencies: 245
-- Data for Name: answers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.answers (id, text, question_id, submission_id) FROM stdin;
1	A	1	1
2	B	2	1
3	A	1	2
4	A	2	2
5	A	1	14
6	B	2	14
7	B	1	15
8	B	2	15
9	A	1	16
10	B	2	16
11	A	1	17
12	B	2	17
13	A	1	18
14	B	2	18
15	A	1	19
16	B	2	19
17	A	1	20
18	B	2	20
19	A	1	21
20	B	2	21
21	C	3	21
22	B	1	22
23	A	2	22
24	A	3	22
25	A	1	23
26	A	2	23
27	A	3	23
28	B	2	24
29	A	1	25
30	A	2	25
31	A	3	25
32	A	4	25
33	A	5	25
34	A	6	25
35	A	1	26
36	A	2	26
37	B	3	26
38	A	4	26
39	B	5	26
40	A	6	26
41	B	1	27
42	A	2	27
43	B	3	27
44	B	4	27
45	C	5	27
46	A	6	27
\.


--
-- TOC entry 5186 (class 0 OID 32952)
-- Dependencies: 227
-- Data for Name: applications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.applications (id, candidate_name, candidate_email, campaign_id, status, submitted_at, created_at, updated_at, user_id, job_id, cv_url, score_exam, type, candidate_id) FROM stdin;
37	\N	\N	\N	APPROVED	2025-11-19 17:42:46.494919	2025-11-19 17:42:46.45074+07	2025-11-20 22:03:05.729182+07	17	9	\N	\N	\N	\N
38	\N	\N	\N	APPROVED	2025-11-19 17:45:33.409525	2025-11-19 17:45:33.368908+07	2025-11-20 22:05:49.073401+07	9	8	\N	\N	\N	\N
36	\N	\N	\N	REJECTED	2025-11-19 04:08:43.397711	2025-11-19 04:08:43.393819+07	2025-11-20 22:23:00.726228+07	18	7	\N	\N	\N	\N
\.


--
-- TOC entry 5188 (class 0 OID 32967)
-- Dependencies: 229
-- Data for Name: audit_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.audit_logs (id, actor, action, details, created_at) FROM stdin;
\.


--
-- TOC entry 5184 (class 0 OID 32938)
-- Dependencies: 225
-- Data for Name: campaigns; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.campaigns (id, code, title, description, start_date, end_date, status) FROM stdin;
\.


--
-- TOC entry 5198 (class 0 OID 73976)
-- Dependencies: 239
-- Data for Name: exams; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exams (id, title, description, job_id, duration, active, created_at, updated_at) FROM stdin;
2	Frontend Skills Test	Bài thi kiểm tra HTML, CSS, JS	\N	25	t	2025-11-18 01:34:39.974893	2025-11-18 01:34:39.974893
1	The cabin crew promotion exam	The following topics: flight safety, cabin procedures, customer service communication, situational handling, and aviation English	\N	30	t	2025-11-18 01:34:39.974893	2025-11-18 01:34:39.974893
\.


--
-- TOC entry 5178 (class 0 OID 32887)
-- Dependencies: 219
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init	SQL	V1__init.sql	1324904260	postgres	2025-11-01 02:16:09.204132	65	t
\.


--
-- TOC entry 5196 (class 0 OID 73866)
-- Dependencies: 237
-- Data for Name: interview; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.interview (id, comment, interview_date, location, score, score_exam, status, application_id, note) FROM stdin;
6	\N	\N	TP. Hồ Chí Minh	9	\N	\N	36	nothing
7	\N	\N	Ha Noi	\N	\N	\N	36	Nothing
8	\N	\N	Ha Noi	\N	\N	\N	36	Nhớ mang theo CV giấy
\.


--
-- TOC entry 5194 (class 0 OID 65657)
-- Dependencies: 235
-- Data for Name: interviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.interviews (id, comment, interview_date, location, note, score, status, application_id, score_exam) FROM stdin;
1	\N	\N	TP. Ho Chi Minh	Mang theo CV giay	\N	\N	37	\N
2	\N	\N	Đà Nẵng	Mang theo CV	\N	\N	38	\N
3	\N	\N	Ha Noi	Mang theo CV	\N	\N	36	\N
\.


--
-- TOC entry 5190 (class 0 OID 32998)
-- Dependencies: 231
-- Data for Name: jobs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jobs (id, created_at, updated_at, description, location, salary_range, status, title, recruiter_id) FROM stdin;
7	2025-11-19 04:07:24.004603+07	2025-11-19 04:07:24.004603+07	Chăm sóc khách, đảm bảo an toàn bay, hỗ trợ tổ bay.	TP. Hồ Chí Minh	18 - 35 triệu/ tháng	OPEN	Cabin Crew (Tiếp viên hàng không)	11
8	2025-11-19 04:07:55.389204+07	2025-11-19 04:07:55.389204+07	Hỗ trợ tiếp viên chính trong phục vụ hành khách.	TP. HCM / Hà Nội / Đà Nẵng	12-18 triệu/ tháng	OPEN	Junior Cabin Crew (Tiếp viên tập sự)	11
9	2025-11-19 04:08:19.193281+07	2025-11-19 04:08:19.193281+07	Giám sát đội ngũ tiếp viên Junior & Cabin Crew.	TP. HCM / Hà Nội	28-40 triệu/ tháng	OPEN	Senior Cabin Crew (Tiếp viên chính)	11
\.


--
-- TOC entry 5210 (class 0 OID 82080)
-- Dependencies: 251
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifications (id, created_at, updated_at, message, read_status, title, user_id) FROM stdin;
1	2025-11-19 17:35:18.56543+07	2025-11-19 17:35:18.56543+07	Ngày: null\nĐịa điểm: Ha Noi\nGhi chú: Nhớ mang theo CV giấy	f	Lịch phỏng vấn mới	18
2	2025-11-19 17:43:22.781103+07	2025-11-19 17:43:22.781103+07	Ngày: null\nĐịa điểm: TP. Ho Chi Minh\nGhi chú: Mang theo CV giay	f	Lịch phỏng vấn mới	17
3	2025-11-19 17:46:10.864359+07	2025-11-19 17:46:10.864359+07	Ngày: null\nĐịa điểm: Đà Nẵng\nGhi chú: Mang theo CV	f	Lịch phỏng vấn mới	9
4	2025-11-20 22:23:23.160753+07	2025-11-20 22:23:23.160753+07	Ngày: null\nĐịa điểm: Ha Noi\nGhi chú: Mang theo CV	f	Lịch phỏng vấn mới	18
\.


--
-- TOC entry 5208 (class 0 OID 74121)
-- Dependencies: 249
-- Data for Name: promotion_applications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.promotion_applications (id, created_at, updated_at, certificates, flight_hours, performance_result, score_exam, status, team_lead_suggestion, candidate_id) FROM stdin;
1	2025-11-18 22:03:57.526881+07	2025-11-19 01:56:06.690739+07	Customer Service Certificate	50	Tốt	\N	APPROVED	Rất phù hợp để nâng bậc	17
2	2025-11-18 23:45:44.065981+07	2025-11-19 02:34:58.49312+07	Safety Training	88	Khá	\N	APPROVED	Khả năng dẫn dắt tổ bay khá tốt, thường hỗ trợ đồng đội.	13
3	2025-11-19 03:18:10.504411+07	2025-11-19 03:48:43.510405+07	Chứng chỉ sơ cứu nâng cao	50	Trung bình	\N	APPROVED	Phù hợp để nâng bậc	9
4	2025-11-19 03:55:53.030348+07	2025-11-20 22:23:44.581478+07	Customer Service Certificate\nSafety Training\nIELTS 7.5	99	Xuất sắc	\N	APPROVED	Khả năng dẫn dắt tổ bay khá tốt, thường hỗ trợ đồng đội.	18
\.


--
-- TOC entry 5206 (class 0 OID 74103)
-- Dependencies: 247
-- Data for Name: promotion_requests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.promotion_requests (id, created_at, updated_at, certificates, flight_hours, performance_result, status, team_lead_suggestion, user_id) FROM stdin;
\.


--
-- TOC entry 5200 (class 0 OID 73990)
-- Dependencies: 241
-- Data for Name: questions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.questions (id, exam_id, content, option_a, option_b, option_c, option_d, correct_answer, created_at, updated_at, optiona, optionb, optionc, optiond) FROM stdin;
1	1	Khi phát hiện khói trong cabin, bước đầu tiên tiếp viên cần làm là gì?	Xác định nguồn khói và báo cáo ngay cho trưởng tiếp viên	Mở cửa thoát hiểm ngay lập tức	Thông báo hành khách giữ bình tĩnh	Tắt đèn cabin	A	\N	\N	\N	\N	\N	\N
3	1	Hành khách phàn nàn về chỗ ngồi chật, thái độ phù hợp nhất là:	Xin lỗi và giải thích rằng hãng không thể làm gì	Đề nghị họ cố gắng chịu đựng vì chuyến bay ngắn	Lắng nghe, xin lỗi, và tìm giải pháp phù hợp trong khả năng cho phép	Yêu cầu họ ngồi yên để tránh làm phiền người khác	C	\N	\N	\N	\N	\N	\N
4	1	Một hành khách khó chịu vì phải chờ lâu để được phục vụ. Tiếp viên nên:	Nói rằng bạn đang làm đúng quy trình	Xin lỗi, xác thực cảm xúc của khách và phục vụ ngay khi có thể	Giải thích rằng chuyến bay đông nên phải chờ	Bỏ qua vì không phải lỗi của mình	B	\N	\N	\N	\N	\N	\N
5	1	Một hành khách đột ngột bất tỉnh, việc đầu tiên là:	Hét lớn để gọi sự chú ý	Kiểm tra phản ứng và nhịp thở ngay lập tức	Tìm hành lý của họ	Đưa nước cho họ uống	B	\N	\N	\N	\N	\N	\N
6	1	Từ nào mô tả trạng thái máy bay rung lắc do không khí?	Turbulence	Touchdown	Takeoff	Decompression	A	\N	\N	\N	\N	\N	\N
2	1	Khi máy bay gặp nhiễu động mạnh (turbulence), tiếp viên phải:	Tiếp tục phục vụ cho đến khi có lệnh dừng	Dừng mọi hoạt động và ngồi vào ghế, thắt dây an toàn	Yêu cầu hành khách đứng lên để ổn định trọng tâm máy bay	Mở khoang hành lý để kiểm tra	B	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 5180 (class 0 OID 32905)
-- Dependencies: 221
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
4	ADMIN
5	RECRUITER
6	CANDIDATE
\.


--
-- TOC entry 5202 (class 0 OID 74012)
-- Dependencies: 243
-- Data for Name: submissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.submissions (id, user_id, exam_id, application_id, score, created_at, updated_at, submitted_at, answers_json) FROM stdin;
1	13	1	\N	100	2025-11-18 07:53:04.922227	2025-11-18 07:53:04.922227	2025-11-18 14:53:04.916639	{"1":"A","2":"B"}
2	17	1	\N	50	2025-11-18 08:17:22.022267	2025-11-18 08:17:22.022267	2025-11-18 15:17:22.008597	{"1":"A","2":"A"}
3	17	1	\N	0	2025-11-18 08:53:59.560509	2025-11-18 08:53:59.560509	2025-11-18 15:53:59.55652	{}
4	17	1	\N	0	2025-11-18 09:00:37.836415	2025-11-18 09:00:37.836415	2025-11-18 16:00:37.834201	{}
5	13	1	\N	0	2025-11-18 09:22:39.670511	2025-11-18 09:22:39.670511	2025-11-18 16:22:39.669438	{}
6	13	1	\N	0	2025-11-18 09:34:14.950614	2025-11-18 09:34:14.950614	2025-11-18 16:34:14.946481	{}
7	13	1	\N	0	2025-11-18 09:35:36.854163	2025-11-18 09:35:36.854163	2025-11-18 16:35:36.850511	{}
8	13	1	\N	0	2025-11-18 09:39:07.856084	2025-11-18 09:39:07.856084	2025-11-18 16:39:07.852	{}
9	13	1	\N	0	2025-11-18 10:02:50.583982	2025-11-18 10:02:50.583982	2025-11-18 17:02:50.580414	{}
10	17	1	\N	0	2025-11-18 10:08:01.05731	2025-11-18 10:08:01.05731	2025-11-18 17:08:01.052001	{}
11	17	1	\N	0	2025-11-18 10:08:26.558814	2025-11-18 10:08:26.558814	2025-11-18 17:08:26.557766	{}
12	17	1	\N	0	2025-11-18 10:08:29.910563	2025-11-18 10:08:29.910563	2025-11-18 17:08:29.910563	{}
13	13	1	\N	0	2025-11-18 10:08:56.145405	2025-11-18 10:08:56.145405	2025-11-18 17:08:56.144358	{}
14	13	1	\N	100	2025-11-18 10:13:35.025094	2025-11-18 10:13:35.025094	2025-11-18 17:13:35.021652	{"1":"A","2":"B"}
15	13	1	\N	50	2025-11-18 10:13:40.606165	2025-11-18 10:13:40.606165	2025-11-18 17:13:40.605597	{"1":"B","2":"B"}
16	13	1	\N	100	2025-11-18 10:22:25.3776	2025-11-18 10:22:25.3776	2025-11-18 17:22:25.374423	{"1":"A","2":"B"}
17	13	1	\N	100	2025-11-18 10:40:41.916018	2025-11-18 10:40:41.916018	2025-11-18 17:40:41.913943	{"1":"A","2":"B"}
18	17	1	\N	100	2025-11-18 11:47:07.41915	2025-11-18 11:47:07.41915	2025-11-18 18:47:07.416113	{"1":"A","2":"B"}
19	17	1	\N	100	2025-11-18 20:15:34.010817	2025-11-18 20:15:34.010817	2025-11-19 03:15:34.007691	{"1":"A","2":"B"}
20	18	1	\N	100	2025-11-18 20:56:27.611125	2025-11-18 20:56:27.611125	2025-11-19 03:56:27.608894	{"1":"A","2":"B"}
21	18	1	\N	100	2025-11-18 21:33:13.428122	2025-11-18 21:33:13.428122	2025-11-19 04:33:13.425921	{"1":"A","2":"B","3":"C"}
22	18	1	\N	0	2025-11-18 21:33:20.546096	2025-11-18 21:33:20.546096	2025-11-19 04:33:20.546096	{"1":"B","2":"A","3":"A"}
23	18	1	\N	33	2025-11-18 21:33:43.01488	2025-11-18 21:33:43.01488	2025-11-19 04:33:43.01488	{"1":"A","2":"A","3":"A"}
24	18	2	\N	100	2025-11-18 21:36:38.741544	2025-11-18 21:36:38.741544	2025-11-19 04:36:38.740537	{"2":"B"}
25	17	1	\N	33	2025-11-20 03:00:59.034203	2025-11-20 03:00:59.034203	2025-11-20 10:00:59.032127	{"1":"A","2":"A","3":"A","4":"A","5":"A","6":"A"}
26	9	1	\N	50	2025-11-20 15:14:30.630699	2025-11-20 15:14:30.630699	2025-11-20 22:14:30.627572	{"1":"A","2":"A","3":"B","4":"A","5":"B","6":"A"}
27	9	1	\N	33	2025-11-20 15:21:57.958069	2025-11-20 15:21:57.958069	2025-11-20 22:21:57.958069	{"1":"B","2":"A","3":"B","4":"B","5":"C","6":"A"}
\.


--
-- TOC entry 5192 (class 0 OID 33047)
-- Dependencies: 233
-- Data for Name: tests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tests (id, created_at, updated_at, description, title, job_id) FROM stdin;
1	2025-11-02 03:52:10.152682+07	2025-11-02 03:52:10.152682+07	Basic Java and OOP questions	Java Developer Test	\N
\.


--
-- TOC entry 5182 (class 0 OID 32916)
-- Dependencies: 223
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, email, full_name, role_id, active, created_at, updated_at, avatar_url, bio, cv_url) FROM stdin;
14	admin	$2a$10$1nYZPREWoO8kZJJNWKeq3ufDpenMiUcOUQaGsCuIcBGmf./lF.1KO	admin@example.com	Administrator	4	t	2025-11-15 08:42:52.275453	2025-11-15 08:42:52.275453	\N	\N	\N
11	tuyendung	$2a$10$WkK.cZtRFrjisJpkWvfkxe.JFcyL3ZCAU3zrYAvTPZGOkl.krA1HW	tuyendung@gmail.com	\N	5	t	2025-11-11 12:59:03.254146	2025-11-11 12:59:03.254146	\N	\N	\N
15	recruiter1	$2a$10$LT6d5/WcHMlCfYIPSd/m4usorM0PoQ6ic8usGlxi6XxVeP2nDTkjy	recruiter1@example.com	Recruiter One	5	t	2025-11-15 08:42:52.418422	2025-11-15 08:42:52.418422	\N	\N	\N
10	oai2	$2a$10$7L7/P0xt86.tsHfdnhhvkud4tEeQVz4071jJ3UNKEMui.z8pLcvj.	oainguyen@gmail.com	\N	6	t	2025-11-11 12:24:19.875887	2025-11-11 12:24:19.875887	\N	\N	\N
7	oai	$2a$10$13qJTeRNrqfvjjUgFIVruOl6thGKdH/2VCvzKgQvoYktQGraRa6pm	oai@gmail.com	Nguyen Van Oai	6	t	2025-11-03 06:52:51.132917	2025-11-03 06:52:51.132917	\N	\N	\N
9	nguyenoai	$2a$10$lnQPu2G5oxycKOI/I1IFROvoiPGNMKjVXqS99gcCoEt0CU9nmuUYa	oai123@gmail.com	\N	6	t	2025-11-11 01:23:26.333816	2025-11-11 01:23:26.333816	\N	\N	\N
1	vana	$2a$10$0/u5bL7sN7Dd9aKshq7dcunOputvqUgrU93tUZ4M2xq23pJA1wzoG	updated@example.com	Nguyen Van Updated	6	t	2025-10-31 20:43:18.766708	2025-11-02 05:40:05.96945	\N	\N	\N
12	tuyendungA	$2a$10$QOFEoBHzaDIVB0JKDUxHuePY1sTlM/UPRJDQ0PC1JP3IK4daWPkAa	tuyendungA@gmail.com	\N	6	t	2025-11-12 10:22:38.302889	2025-11-12 10:22:38.302889	\N	\N	\N
13	test	$2a$10$JHH.NPLpVGbaRFw28FQLb.yAKoYlHLyVkI8KTfaB5Pz9STNnBfBpW	test@gmail.com	\N	6	t	2025-11-14 11:11:46.034546	2025-11-14 11:11:46.034546	\N	\N	\N
16	candidate1	$2a$10$xduzGPGDB04teLX659CR6.kOaTaFmkXNd/xHMJzqgl8S3F9MTYPde	candidate1@example.com	Candidate One	6	t	2025-11-15 08:42:52.54174	2025-11-15 08:42:52.54174	\N	\N	\N
17	ducoai	$2a$10$riPdojUNzr8X3Pt260M15OJjNLEpmuEnRcAvXVkXTLcS8buwr0xqK	ducoai@gmail.com	\N	6	t	2025-11-17 06:08:19.266549	2025-11-17 06:08:19.266549	\N	\N	\N
18	uyen	$2a$10$OzM2PIz9CMMJfFmSGCL5OO1TzLmkL5ofP/zfgMYiW9aeZakbxvEIG	uyen2110@gmail.com	\N	6	t	2025-11-18 20:54:45.909307	2025-11-18 20:54:45.909307	\N	\N	\N
\.


--
-- TOC entry 5232 (class 0 OID 0)
-- Dependencies: 244
-- Name: answers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.answers_id_seq', 46, true);


--
-- TOC entry 5233 (class 0 OID 0)
-- Dependencies: 226
-- Name: applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.applications_id_seq', 43, true);


--
-- TOC entry 5234 (class 0 OID 0)
-- Dependencies: 228
-- Name: audit_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.audit_logs_id_seq', 1, false);


--
-- TOC entry 5235 (class 0 OID 0)
-- Dependencies: 224
-- Name: campaigns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.campaigns_id_seq', 1, false);


--
-- TOC entry 5236 (class 0 OID 0)
-- Dependencies: 238
-- Name: exams_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exams_id_seq', 2, true);


--
-- TOC entry 5237 (class 0 OID 0)
-- Dependencies: 236
-- Name: interview_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interview_id_seq', 8, true);


--
-- TOC entry 5238 (class 0 OID 0)
-- Dependencies: 234
-- Name: interviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interviews_id_seq', 3, true);


--
-- TOC entry 5239 (class 0 OID 0)
-- Dependencies: 230
-- Name: jobs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jobs_id_seq', 9, true);


--
-- TOC entry 5240 (class 0 OID 0)
-- Dependencies: 250
-- Name: notifications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notifications_id_seq', 4, true);


--
-- TOC entry 5241 (class 0 OID 0)
-- Dependencies: 248
-- Name: promotion_applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.promotion_applications_id_seq', 4, true);


--
-- TOC entry 5242 (class 0 OID 0)
-- Dependencies: 246
-- Name: promotion_requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.promotion_requests_id_seq', 1, false);


--
-- TOC entry 5243 (class 0 OID 0)
-- Dependencies: 240
-- Name: questions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.questions_id_seq', 2, true);


--
-- TOC entry 5244 (class 0 OID 0)
-- Dependencies: 220
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 6, true);


--
-- TOC entry 5245 (class 0 OID 0)
-- Dependencies: 242
-- Name: submissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.submissions_id_seq', 27, true);


--
-- TOC entry 5246 (class 0 OID 0)
-- Dependencies: 232
-- Name: tests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tests_id_seq', 1, false);


--
-- TOC entry 5247 (class 0 OID 0)
-- Dependencies: 222
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 18, true);


--
-- TOC entry 5006 (class 2606 OID 74038)
-- Name: answers answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (id);


--
-- TOC entry 4986 (class 2606 OID 32960)
-- Name: applications applications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT applications_pkey PRIMARY KEY (id);


--
-- TOC entry 4990 (class 2606 OID 32976)
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- TOC entry 4982 (class 2606 OID 32950)
-- Name: campaigns campaigns_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_code_key UNIQUE (code);


--
-- TOC entry 4984 (class 2606 OID 32948)
-- Name: campaigns campaigns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_pkey PRIMARY KEY (id);


--
-- TOC entry 5000 (class 2606 OID 73988)
-- Name: exams exams_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exams
    ADD CONSTRAINT exams_pkey PRIMARY KEY (id);


--
-- TOC entry 4971 (class 2606 OID 32902)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4998 (class 2606 OID 73875)
-- Name: interview interview_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interview
    ADD CONSTRAINT interview_pkey PRIMARY KEY (id);


--
-- TOC entry 4996 (class 2606 OID 65667)
-- Name: interviews interviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interviews
    ADD CONSTRAINT interviews_pkey PRIMARY KEY (id);


--
-- TOC entry 4992 (class 2606 OID 33008)
-- Name: jobs jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- TOC entry 5012 (class 2606 OID 82089)
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- TOC entry 5010 (class 2606 OID 74132)
-- Name: promotion_applications promotion_applications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_applications
    ADD CONSTRAINT promotion_applications_pkey PRIMARY KEY (id);


--
-- TOC entry 5008 (class 2606 OID 74114)
-- Name: promotion_requests promotion_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_requests
    ADD CONSTRAINT promotion_requests_pkey PRIMARY KEY (id);


--
-- TOC entry 5002 (class 2606 OID 74004)
-- Name: questions questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- TOC entry 4974 (class 2606 OID 32914)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 4976 (class 2606 OID 32912)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 5004 (class 2606 OID 74022)
-- Name: submissions submissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT submissions_pkey PRIMARY KEY (id);


--
-- TOC entry 4994 (class 2606 OID 33055)
-- Name: tests tests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tests
    ADD CONSTRAINT tests_pkey PRIMARY KEY (id);


--
-- TOC entry 4988 (class 2606 OID 74072)
-- Name: applications unique_candidate; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT unique_candidate UNIQUE (user_id);


--
-- TOC entry 4978 (class 2606 OID 32929)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4980 (class 2606 OID 32931)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4972 (class 1259 OID 32903)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 5026 (class 2606 OID 74039)
-- Name: answers answers_question_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_question_id_fkey FOREIGN KEY (question_id) REFERENCES public.questions(id) ON DELETE CASCADE;


--
-- TOC entry 5027 (class 2606 OID 74044)
-- Name: answers answers_submission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_submission_id_fkey FOREIGN KEY (submission_id) REFERENCES public.submissions(id) ON DELETE CASCADE;


--
-- TOC entry 5014 (class 2606 OID 32961)
-- Name: applications applications_campaign_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT applications_campaign_id_fkey FOREIGN KEY (campaign_id) REFERENCES public.campaigns(id);


--
-- TOC entry 5015 (class 2606 OID 74075)
-- Name: applications fk32iahkg1fqci0pt76uql80nj7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk32iahkg1fqci0pt76uql80nj7 FOREIGN KEY (candidate_id) REFERENCES public.users(id);


--
-- TOC entry 5023 (class 2606 OID 74054)
-- Name: submissions fk760bgu69957phd7hax608jdms; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT fk760bgu69957phd7hax608jdms FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 5029 (class 2606 OID 74133)
-- Name: promotion_applications fk7snphr282bp7ckc65wy2i5unw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_applications
    ADD CONSTRAINT fk7snphr282bp7ckc65wy2i5unw FOREIGN KEY (candidate_id) REFERENCES public.users(id);


--
-- TOC entry 5021 (class 2606 OID 73876)
-- Name: interview fk83ira5y3yd25iclin92mljwgw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interview
    ADD CONSTRAINT fk83ira5y3yd25iclin92mljwgw FOREIGN KEY (application_id) REFERENCES public.applications(id);


--
-- TOC entry 5018 (class 2606 OID 65673)
-- Name: jobs fk9syuy0yp9a2c0fkr98pvxpb8m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT fk9syuy0yp9a2c0fkr98pvxpb8m FOREIGN KEY (recruiter_id) REFERENCES public.users(id);


--
-- TOC entry 5030 (class 2606 OID 82090)
-- Name: notifications fk9y21adhxn0ayjhfocscqox7bh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT fk9y21adhxn0ayjhfocscqox7bh FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 5016 (class 2606 OID 33014)
-- Name: applications fk_app_job; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk_app_job FOREIGN KEY (job_id) REFERENCES public.jobs(id);


--
-- TOC entry 5028 (class 2606 OID 74115)
-- Name: promotion_requests fkdoynwwyoh7kitmne541ih5mcp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.promotion_requests
    ADD CONSTRAINT fkdoynwwyoh7kitmne541ih5mcp FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 5017 (class 2606 OID 32991)
-- Name: applications fkfsfqljedcla632u568jl5qf3w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fkfsfqljedcla632u568jl5qf3w FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 5024 (class 2606 OID 74049)
-- Name: submissions fki66uawn9uygekv6i88hp1cwrd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT fki66uawn9uygekv6i88hp1cwrd FOREIGN KEY (application_id) REFERENCES public.applications(id);


--
-- TOC entry 5020 (class 2606 OID 65668)
-- Name: interviews fkok2bail5ls3jjbjgl5c6nt620; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interviews
    ADD CONSTRAINT fkok2bail5ls3jjbjgl5c6nt620 FOREIGN KEY (application_id) REFERENCES public.applications(id);


--
-- TOC entry 5019 (class 2606 OID 33081)
-- Name: tests fks5yltx2fp6ytuc6g3dulfpo70; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tests
    ADD CONSTRAINT fks5yltx2fp6ytuc6g3dulfpo70 FOREIGN KEY (job_id) REFERENCES public.jobs(id);


--
-- TOC entry 5022 (class 2606 OID 74005)
-- Name: questions questions_exam_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_exam_id_fkey FOREIGN KEY (exam_id) REFERENCES public.exams(id) ON DELETE CASCADE;


--
-- TOC entry 5025 (class 2606 OID 74023)
-- Name: submissions submissions_exam_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT submissions_exam_id_fkey FOREIGN KEY (exam_id) REFERENCES public.exams(id) ON DELETE CASCADE;


--
-- TOC entry 5013 (class 2606 OID 32932)
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


-- Completed on 2025-11-20 22:31:19

--
-- PostgreSQL database dump complete
--

\unrestrict eZJviabC0yeHvY4nLcNXN0Iky082sTnlMygPDe7q90Y84Wjc74oOJZfmmvdtPQc

