from django.urls import path

from . import views

urlpatterns = [
    path("transactions/location/<str:city>", views.city),
    path("transaction/<str:user_id>/location/<str:city>", views.user_city_totals),
]
