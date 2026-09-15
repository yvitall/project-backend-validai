CREATE TABLE users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL CHECK (LENGTH(first_name) >= 3),
    last_name VARCHAR(50) NOT NULL CHECK (LENGTH(last_name) >= 3),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'PARTICIPANT' CHECK (role IN ('ADMIN', 'ORGANIZER', 'PARTICIPANT')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    organizer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL CHECK(LENGTH(title) >= 5),
    description VARCHAR(255) NOT NULL CHECK(LENGTH(description) >= 10),
    location VARCHAR(255) NOT NULL CHECK(LENGTH(location) >= 5),
    date DATE NOT NULL,
    hour TIME NOT NULL,
    workload INTEGER NOT NULL CHECK(workload > 0),
    capacity INTEGER NOT NULL CHECK(capacity > 0),
    speaker_name VARCHAR(50) NOT NULL CHECK(LENGTH(speaker_name) >= 3),
    speaker_title VARCHAR(50) NOT NULL CHECK(LENGTH(speaker_title) >= 3),
    status VARCHAR(20) NOT NULL CHECK (status IN('ACTIVE', 'CANCELED', 'FINISHED')),

    CONSTRAINT fk_events_user FOREIGN KEY (organizer_id)
        REFERENCES users (id)
);

CREATE TABLE registrations(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    registration_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL CHECK(status IN ('ACTIVE', 'CANCELED', 'ATTENDED')),
    attendance_confirmed BOOLEAN DEFAULT FALSE NOT NULL,
    attendance_confirmed_at TIMESTAMP,
    qr_code VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_registrations_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_registrations_event FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT uk_user_event UNIQUE (user_id, event_id)
);

CREATE TABLE certificates(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    registration_id BIGINT NOT NULL UNIQUE,
    issue_date DATE NOT NULL DEFAULT CURRENT_DATE,
    certificate_code VARCHAR(255) NOT NULL UNIQUE,
    pdf_url VARCHAR(255) NOT NULL UNIQUE,

    CONSTRAINT fk_certificates_registrations FOREIGN KEY (registration_id) REFERENCES registrations (id)
);