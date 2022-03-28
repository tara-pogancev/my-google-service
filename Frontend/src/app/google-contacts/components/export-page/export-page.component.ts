import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as FileSaver from 'file-saver';
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
      type: new FormControl('csv', {
        validators: [Validators.required],
      }),
    });
  }

  export() {
    if (this.exportForm.valid) {
      if (this.exportForm.controls.type.value == 'csv') {
        this.exportService.exportAllCsv().subscribe((res) => {
          FileSaver.saveAs(res.body, 'contacts.csv');
        });
      } else {
        this.exportService.exportAllJson().subscribe((res) => {
          FileSaver.saveAs(res.body, 'contacts.json');
        });
      }
    }
  }
}
