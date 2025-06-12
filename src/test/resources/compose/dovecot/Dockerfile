FROM debian:bullseye-slim

ENV DEBIAN_FRONTEND=noninteractive
ENV MAIL_UID=5000
ENV MAIL_GID=5000

RUN apt-get update && \
    apt-get install -y dovecot-core dovecot-sieve dovecot-managesieved && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /maildata && chown -R ${MAIL_UID}:${MAIL_GID} /maildata

COPY ./conf /etc/dovecot

EXPOSE 4190

CMD ["dovecot", "-F"]
