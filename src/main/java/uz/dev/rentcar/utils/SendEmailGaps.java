package uz.dev.rentcar.utils;

import org.springframework.stereotype.Component;
import uz.dev.rentcar.payload.SendEmailBookingDTO;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:48
 **/


@Component
public class SendEmailGaps {

    public String generateBookingCreationHtml(SendEmailBookingDTO dto) {

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>RentCar Booking Notification</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                            color: #333;
                        }
                        .container {
                            width: 100%;
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            overflow: hidden;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #007bff;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        .content {
                            padding: 20px;
                            color: #333;
                        }
                        .content p {
                            font-size: 16px;
                            line-height: 1.5;
                            margin-bottom: 15px;
                        }
                        .btn {
                            display: inline-block;
                            padding: 12px 25px;
                            background-color: #007bff;
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 5px;
                            font-size: 16px;
                            margin-top: 20px;
                        }
                        .btn:hover {
                            background-color: #0056b3;
                        }
                        .footer {
                            text-align: center;
                            padding: 10px;
                            background-color: #f4f4f4;
                            font-size: 12px;
                            color: #777;
                        }
                        @media only screen and (max-width: 600px) {
                            .container {
                                width: 100% !important;
                            }
                            .content p {
                                font-size: 14px;
                            }
                            .btn {
                                padding: 10px 20px;
                                font-size: 14px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>RentCar - Booking Notification</h1>
                        </div>
                        <div class="content">
                            <p>Assalomu alaykum, hurmatli mijoz!</p>
                            <p>Sizning yangi bron qilish so‘rovingiz muvaffaqiyatli qabul qilindi. Quyidagi avtomobil uchun booking holati hozircha <strong>To'lov kutilyabdi</strong> holatida:</p>
                            <p><strong>Avtomobil:</strong> %s <br>
                               <strong>Bron ID:</strong> %s <br>
                               <strong>Sana:</strong> %s <br>
                               <strong>Jami narx:</strong> %s so'm</p>
                            <p>Bronni tasdiqlash uchun iltimos, to‘lovni amalga oshiring. To‘lovni bajarish uchun quyidagi tugmani bosing:</p>
                            <a href="[Payment Link]" class="btn">To‘lovni Bajarish</a>
                            <p>Agar savollaringiz bo‘lsa, bizning qo‘llab-quvvatlash jamoamiz bilan bog‘laning: <a href="mailto:support@rentcar.com">support@rentcar.com</a></p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2025 RentCar. Barcha huquqlar himoyalangan.</p>
                        </div>
                    </div>
                </body>
                </html>""".formatted(
                dto.getCarBrandAndModel(),
                dto.getBookingId(),
                dto.getCreatedAt(),
                dto.getTotalPrice()
        );

    }

    public String generateBookingCancelledHtml(Long id, LocalDateTime cancellationDate) {

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>RentCar Booking Cancellation</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                            color: #333;
                        }
                        .container {
                            width: 100%;
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            overflow: hidden;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #dc3545;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        .content {
                            padding: 20px;
                            color: #333;
                        }
                        .content p {
                            font-size: 16px;
                            line-height: 1.5;
                            margin-bottom: 15px;
                        }
                        .btn {
                            display: inline-block;
                            padding: 12px 25px;
                            background-color: #007bff;
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 5px;
                            font-size: 16px;
                            margin-top: 20px;
                        }
                        .btn:hover {
                            background-color: #0056b3;
                        }
                        .footer {
                            text-align: center;
                            padding: 10px;
                            background-color: #f4f4f4;
                            font-size: 12px;
                            color: #777;
                        }
                        @media only screen and (max-width: 600px) {
                            .container {
                                width: 100% !important;
                            }
                            .content p {
                                font-size: 14px;
                            }
                            .btn {
                                padding: 10px 20px;
                                font-size: 14px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>RentCar - Booking Cancellation</h1>
                        </div>
                        <div class="content">
                            <p>Assalomu alaykum, hurmatli mijoz!</p>
                            <p>Sizning bron qilish so‘rovingiz bekor qilindi.</p>
                            <p><strong>Bron ID:</strong> %s <br>
                               <strong>Bekor qilish vaqti:</strong> %s</p>
                            <p>Agar bu xatolik bo‘lsa yoki qo‘shimcha ma’lumot kerak bo‘lsa, iltimos, bizning qo‘llab-quvvatlash jamoamiz bilan bog‘laning: <a href="mailto:support@rentcar.com">support@rentcar.com</a></p>
                            <p>Yana bir bor RentCar xizmatlaridan foydalanishingizni kutamiz!</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2025 RentCar. Barcha huquqlar himoyalangan.</p>
                        </div>
                    </div>
                </body>
                </html>""".formatted(
                id,
                cancellationDate
        );

    }

    public String generateBookingConfirm(Long bookingId) {

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>RentCar Payment Confirmation</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                            color: #333;
                        }
                        .container {
                            width: 100%;
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            overflow: hidden;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #28a745;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 24px;
                        }
                        .content {
                            padding: 20px;
                            color: #333;
                        }
                        .content p {
                            font-size: 16px;
                            line-height: 1.5;
                            margin-bottom: 15px;
                        }
                        .footer {
                            text-align: center;
                            padding: 10px;
                            background-color: #f4f4f4;
                            font-size: 12px;
                            color: #777;
                        }
                        @media only screen and (max-width: 600px) {
                            .container {
                                width: 100% !important;
                            }
                            .content p {
                                font-size: 14px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>RentCar - To‘lov Tasdiqlandi!</h1>
                        </div>
                        <div class="content">
                            <p>Assalomu alaykum, maroqli sayohatchi!</p>
                            <p>Sizning <strong>Bron ID: %s </strong> uchun to‘lov muvaffaqiyatli tasdiqlandi! Endi yo‘lingiz ochiq – maroqli hordiq chiqaring va sayohatingizni lazzat bilan boshlang!</p>
                            <p>Agar qo‘shimcha yordam kerak bo‘lsa, biz bilan bog‘laning: <a href="mailto:support@rentcar.com">support@rentcar.com</a></p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2025 RentCar. Barcha huquqlar himoyalangan.</p>
                        </div>
                    </div>
                </body>
                </html>""".formatted(bookingId);

    }

    public String generateBookingComplete(Long bookingId) {

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>RentCar Return Notification</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                            color: #333;
                        }
                        .container {
                            width: 100%;
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 4px;
                            overflow: hidden;
                        }
                        .header {
                            background-color: #17a2b8;
                            color: #ffffff;
                            text-align: center;
                            padding: 15px;
                        }
                        .header h1 {
                            margin: 0;
                            font-size: 20px;
                        }
                        .content {
                            padding: 15px;
                            color: #333;
                        }
                        .content p {
                            font-size: 14px;
                            line-height: 1.4;
                            margin-bottom: 10px;
                        }
                        .footer {
                            text-align: center;
                            padding: 10px;
                            background-color: #f4f4f4;
                            font-size: 12px;
                            color: #777;
                        }
                        @media only screen and (max-width: 600px) {
                            .container {
                                width: 100% !important;
                            }
                            .content p {
                                font-size: 12px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>RentCar - Avtomobil Qaytarildi</h1>
                        </div>
                        <div class="content">
                            <p>Assalomu alaykum!</p>
                            <p>Sizning <strong>Bron ID: %s</strong> uchun ijaraga olingan avtomobil bugun qaytarildi. Sayohatingiz uchun rahmat!</p>
                            <p>Savollar bo‘lsa, <a href="mailto:support@rentcar.com">support@rentcar.com</a> ga yozing.</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2025 RentCar. Barcha huquqlar himoyalangan.</p>
                        </div>
                    </div>
                </body>
                </html>""".formatted(
                bookingId
        );

    }

}