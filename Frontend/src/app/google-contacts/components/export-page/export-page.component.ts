import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'export-page',
  templateUrl: './export-page.component.html',
  styleUrls: ['./export-page.component.scss'],
})
export class ExportPageComponent implements OnInit {
  exportForm: FormGroup = new FormGroup({});

  constructor(private exportService: ExportService) {}

  ngOnInit(): void {
    this.exportForm = new FormGroup({
      type: new FormControl('1', {
        validators: [Validators.required],
      }),
    });
  }

  export() {
    if (this.exportForm.valid) {
      this.exportService.exportAllCsv().subscribe((res) => {
        var url = window.URL.createObjectURL(res.body);
        window.open(url);
      });
    }
  }
}
