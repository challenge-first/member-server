package com.example.memberserver.member.entity;

import com.example.memberserver.member.exception.DepositException;
import com.example.memberserver.member.exception.PointException;
import com.example.memberserver.member.role.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Long point;

    @Column(nullable = false)
    private Long deposit;

    public void rechargePoint(Long point) {
        this.point += point;
    }

    @Builder
    private Member(String username, String password, Role role, Long point, Long deposit) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.point = point;
        this.deposit = deposit;
    }

    public void payPoint(Long price) {
        Long availablePoint = getAvailablePoint();
        Long balance = availablePoint - price;
        if (balance < 0) {
            throw new PointException("결제 금액은 가용 포인트보다 클 수 없습니다.");
        }
        this.point = balance;
    }

    public void subtractPointsOnBidSuccess() {
        Long balance = this.point - this.deposit;
        if (balance < 0) {
            throw new DepositException("예치금은 포인트보다 클 수 없습니다.");
        }
        this.point = balance;
        resetDeposit();
    }

    public void setDeposit(Long bid) {
        if (point < bid) {
            throw new DepositException("예치금은 포인트보다 클 수 없습니다.");
        }
        this.deposit = bid;
    }

    public Long getAvailablePoint() {
        if (point - deposit < 0) {
            throw new PointException("잘못된 포인트 입니다.");
        }
        return point - deposit;
    }

    public void resetDeposit() {
        this.deposit = 0L;
    }
}
