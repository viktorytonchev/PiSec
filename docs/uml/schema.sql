CREATE TABLE pisec.system (
	sid integer NOT NULL,
	name character varying(100)
);

CREATE TABLE pisec.notifications (
	nid integer NOT NULL,
	message character varying,
	"date" DATE,
	"time" time without time zone
);

CREATE TABLE pisec.email (
	eid integer NOT NULL,
	email character varying(100),
	sid integer NOT NULL
);

CREATE TABLE pisec.sensor (
	sid integer NOT NULL,
	armed boolean DEFAULT FALSE,
	"open" boolean DEFAULT FALSE,
	name character varying(100)
);

CREATE TABLE pisec.is_sent_to (
	eid integer NOT NULL,
	nid integer NOT NULL
);

ALTER TABLE ONLY pisec.system
	ADD CONSTRAINT system_pkey PRIMARY KEY (sid);

ALTER TABLE ONLY pisec.notifications
	ADD CONSTRAINT notifications_pkey PRIMARY KEY (nid);

ALTER TABLE ONLY pisec.email
	ADD CONSTRAINT email_pkey PRIMARY KEY (eid);

ALTER TABLE ONLY pisec.email
	ADD CONSTRAINT unique_email UNIQUE (email);

ALTER TABLE ONLY pisec.sensor
	ADD CONSTRAINT sensor_pkey PRIMARY KEY (sid, name);

ALTER TABLE ONLY pisec.is_sent_to
	ADD CONSTRAINT is_sent_to_pkey PRIMARY KEY (eid, nid);

ALTER TABLE ONLY pisec.email
	ADD CONSTRAINT email_sid_fkey FOREIGN KEY (sid) REFERENCES pisec.system(sid);

ALTER TABLE ONLY pisec.sensor
	ADD CONSTRAINT sensor_sid_fkey FOREIGN KEY (sid) REFERENCES pisec.system(sid);

ALTER TABLE ONLY pisec.is_sent_to
	ADD CONSTRAINT is_sent_to_nid_fkey FOREIGN KEY (nid) REFERENCES pisec.notifications(nid);

ALTER TABLE ONLY pisec.is_sent_to
	ADD CONSTRAINT is_sent_to_eid_fkey FOREIGN KEY (eid) REFERENCES pisec.email(eid);
