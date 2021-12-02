# Special variable that sets the default target
.DEFAULT_GOAL := help


# 'override' keyword prevents the variable from being overrideen
override MODULE_NAME := openrota-backend
override RED := \033[0;31m
override BLUE := \033[36m
override NC := \033[0m
override BGreen := \033[1;32m



# You can override these variables from the command line.
BUILD_FLAGS ?=
SKIP_TESTS ?= false
IMAGE_ORG ?= openrota
IMAGE_REPO ?= docker.io
IMAGE_TAG ?= latest
DOCKERFILE_NAME ?= Dockerfile.jvm
DOCKERFILE_PATH ?= ./src/main/docker
IMAGE_BUILD_WORKSPACE ?= $(shell pwd)



.PHONY: help ## run 'make' or 'make help' to get a list of available targets and their description
help:
	@echo ""
	@echo "================================================================="
	@printf "$(BGreen)Please use 'make <target>', where target is one of:-$(NC)\n"
	@echo "================================================================="
	@grep -E '^\.PHONY: [a-zA-Z_-]+ .*?## .*$$' $(MAKEFILE_LIST)  | awk 'BEGIN {FS = "(: |##)"}; {printf "\033[36m%-42s\033[0m %s\n", $$2, $$3}'
	@echo ""
	@echo "================================================================="
	@printf "$(BGreen)Variables available for override:-$(NC)\n"		
	@echo "================================================================="
	@printf "$(BLUE)SKIP_TESTS$(NC)             Skips Tests. The Default value is 'false'\n"
	@printf "$(BLUE)BUILD_FLAGS$(NC)            Additional maven build flags. By Default, it doesn't pass any additional flags.\n"
	@printf "$(BLUE)IMAGE_ORG$(NC)              User/Organization of the Image. Default is 'openrota'.\n"
	@printf "$(BLUE)IMAGE_REPO$(NC)             Image Repository of the image. Default is 'docker.io'.\n"
	@printf "$(BLUE)IMAGE_TAG$(NC)              Image tag. Default is 'latest'.\n"
	@printf "$(BLUE)DOCKERFILE_NAME$(NC)        Name of the dockerfile. Default is 'Dockerfile.jvm'.\n"
	@printf "$(BLUE)DOCKERFILE_LOCATION$(NC)    Path to the dockerfile. Default is './src/main/docker'.\n"
	@printf "$(BLUE)IMAGE_BUILD_WORKSPACE$(NC)  Image build workspace. Default is '$(pwd)'.\n"
	@echo ""



.PHONY: build-project ## Build and test all modules
build-project:
	@echo "======================================================================"
	@echo " Building Module '$(MODULE_NAME)'"
	@echo " SKIP_TESTS: $(SKIP_TESTS)"
	@echo " BUILD_FLAGS: $(BUILD_FLAGS)"
	@echo "======================================================================"
	./mvnw clean install -Dquarkus.package.type=fast-jar -DskipTests=$(SKIP_TESTS) $(BUILD_FLAGS)



.PHONY: build-image ## Build docker image
build-image:
	@echo "======================================================================"
	@echo " Building Image $(IMAGE_REPO)/$(IMAGE_ORG)/$(MODULE_NAME):$(IMAGE_TAG)"
	@echo "======================================================================"
	docker build -f $(DOCKERFILE_PATH)/$(DOCKERFILE_NAME) -t $(IMAGE_REPO)/$(IMAGE_ORG)/$(MODULE_NAME):$(IMAGE_TAG) $(IMAGE_BUILD_WORKSPACE)


.PHONY: push-image ## Push docker image
push-image:
	@echo "======================================================================"
	@echo " Pushing Image $(IMAGE_REPO)/$(IMAGE_ORG)/$(MODULE_NAME):$(IMAGE_TAG)"
	@echo "======================================================================"
	docker push $(IMAGE_REPO)/$(IMAGE_ORG)/$(MODULE_NAME):$(IMAGE_TAG)


# Please declare your targets as .PHONY in the format shown below, so that the 'make help' parses the information correctly.
#
# .PHONY: <target-name>  ## Description of what target does
