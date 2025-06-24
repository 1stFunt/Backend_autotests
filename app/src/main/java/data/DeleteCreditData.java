package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteCreditData {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isActive;
    private boolean inArchive;

    private void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public DeleteCreditData(boolean isActive, boolean inArchive) {
        this.setIsActive(isActive);
        this.setInArchive(inArchive);
    }

    public DeleteCreditData(boolean isActive) {
        this.setIsActive(isActive);
    }
}