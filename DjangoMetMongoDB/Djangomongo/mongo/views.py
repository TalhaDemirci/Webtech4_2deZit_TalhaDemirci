from django.shortcuts import render
from pymongo import *
from django.urls import reverse
from django.shortcuts import render, redirect

# Create your views here.



client = MongoClient("localhost", 27017)
db = client.recept
collection = db['recept']

def index(request):

    recept_list=[]
    cursor = collection.find({})
    for document in cursor:
        recept_list.append(document)
    return render(request, 'index.html', {'recept_list': recept_list})

def add_recept(request):
    if request.method == 'POST':
        name = request.POST.get('name').title().strip()
        aantalcalorieen = request.POST.get('aantalcalorieen').title().strip()
        ingredienten = request.POST.get('ingredienten').title().strip()
        tijd = request.POST.get('tijd').title().strip()
        db.recept.insert({"recept_name":name,"aantalcalorieen":aantalcalorieen,"ingredienten":ingredienten,"tijd":tijd})
        return redirect(reverse('mongo:index'))
    else:
        return render(request, 'add_recept.html')