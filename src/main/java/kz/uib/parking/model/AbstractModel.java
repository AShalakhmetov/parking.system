package kz.uib.parking.model;

import java.util.UUID;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class AbstractModel {

    // UUID строки в таблице. Должно генерится во время создания ровно один раз
    private String id;

    /**
     * Любая вновь созданная модель должна иметь уникальный ID
     */
    public AbstractModel() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
