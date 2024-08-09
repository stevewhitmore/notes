FROM squidfunk/mkdocs-material:9

RUN --mount=type=bind,source=requirements.txt,target=requirements.txt,readonly \
    pip install \
    --no-cache-dir \
    --requirement requirements.txt

ENTRYPOINT ["mkdocs"]

CMD ["serve", "--dev-addr=0.0.0.0:8000"]
