FROM java:8
COPY src/com/selfsegregation/ com/selfsegregation/
WORKDIR /com/selfsegregation
RUN javac -d . *.java
CMD ["java", "com/selfsegregation/Main"]