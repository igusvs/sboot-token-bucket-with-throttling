FROM ubuntu:latest
LABEL authors="igors"

ENTRYPOINT ["top", "-b"]