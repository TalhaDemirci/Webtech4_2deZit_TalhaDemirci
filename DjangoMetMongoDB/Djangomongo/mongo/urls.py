from django.conf.urls import url

from . import views

app_name='mongo'
urlpatterns = [
	url(r'^$', views.index, name='index'),
	url(r'^add_recept/$', views.add_recept, name='add_recept'),
]
