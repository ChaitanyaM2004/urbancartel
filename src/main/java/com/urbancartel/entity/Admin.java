package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder//FOR INHERITANCE
@PrimaryKeyJoinColumn(name="user_id")

//enum in java is fixed set of constant values which cant be changed here are some examples of enum
/*
You might write:
class Days {
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
}
Problems:
You can accidentally pass 99 → which is not a valid day.
It’s harder to read.
🔹 Example with enum
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
Now:
Day today = Day.MONDAY;
System.out.println(today);   // MONDAY
✅ Only valid days can be used.
🔹 Why use enum?
Readable → Day.MONDAY is clearer than 1.
Type-safe → You can’t accidentally assign 99.
Can have methods → Enums can behave like classes.
🔹 Enum with methods
enum Season {
    WINTER, SPRING, SUMMER, FALL;
    public void printMessage() {
        System.out.println("Season is " + this);
    }
}
Usage:
Season s = Season.SUMMER;
s.printMessage();   // Season is SUMMER
 */
public class Admin extends User {
     @Enumerated(EnumType.STRING)
     /*
     When you use enums in an entity class (database model), JPA/Hibernate needs to know how to store them in the database.
     Two options:
EnumType.ORDINAL (default)
Stores the position (number) of the enum constant.
Example:
enum Status { NEW, IN_PROGRESS, DONE }
If you save Status.IN_PROGRESS, DB stores:
1   // because IN_PROGRESS is 2nd (0-based index)
⚠ Problem: If you change enum order later, data breaks.
EnumType.STRING
Stores the name (text) of the enum constant.
Example:
Status.IN_PROGRESS
In DB it stores:
"IN_PROGRESS"
✅ Safer, because renaming order won’t break your data.
     */
     @Column(nullable=false,length=50)
    private Roles role;
     @Column(name="is_active")
    private Boolean isActive=true;

}
