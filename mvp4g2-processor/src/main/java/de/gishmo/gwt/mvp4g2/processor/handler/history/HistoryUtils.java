package de.gishmo.gwt.mvp4g2.processor.handler.history;

import de.gishmo.gwt.mvp4g2.processor.ProcessorConstants;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class HistoryUtils {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private HistoryUtils() {
  }

  private HistoryUtils(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;

    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public String createHistoryMetaDataClassName(TypeElement element) {
    return this.processorUtils.setFirstCharacterToUpperCase(this.createHistoryMetaDataVariableName(element)) + "_" + ProcessorConstants.META_DATA;
  }

  public String createHistoryMetaDataVariableName(TypeElement element) {
    return this.processorUtils.createFullClassName(element.asType()
                                                          .toString());
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    /**
     * Set the processing envirement
     *
     * @param processingEnvirement the processing envirement
     * @return the Builder
     */
    public Builder processingEnvironment(ProcessingEnvironment processingEnvirement) {
      this.processingEnvironment = processingEnvirement;
      return this;
    }

    public HistoryUtils build() {
      return new HistoryUtils(this);
    }
  }
}
